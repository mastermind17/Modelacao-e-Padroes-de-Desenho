import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;
import soccerapp.webapi.utils.cache.players.PlayersCache;
import soccerapp.webapi.utils.cache.players.PlayersFileSupplier;
import soccerapp.webapi.utils.cache.players.PlayersHttpSupplier;

import java.io.File;
import java.util.function.Function;

public class LRUTest {

	@Before
	public void cleanDiskCache(){
		File directory = new File("src/main/resources/cache/teams/");
		for(File file: directory.listFiles()) file.delete();
	}

	@Test
	public void testLRUCacheLimit() {
		int[] count = {0};

		SoccerServiceAsyncNio service = new SoccerServiceAsyncNio();
		PlayersHttpSupplier playersHttpSupplier = new PlayersHttpSupplier(service);

		Function<String, String> httpWrapper = (str) -> {
			count[0]++;
			return playersHttpSupplier.apply(str);
		};

		PlayersFileSupplier playersFileSupplier = new PlayersFileSupplier(httpWrapper);
		// Cache limit 2
		PlayersCache playersCache = new PlayersCache(2, playersFileSupplier);

		// Get list of player from team with id 57
		// 1 Http Request
		playersCache.apply("/64");
		// 1 Http Requests - cache is now full
		playersCache.apply("/65");
		// put team 64 as LRU
		playersCache.apply("/64");

		// 1 Http Request - remove LRU entry
		playersCache.apply("/66");
		// clean disk cache to force an http request
		cleanDiskCache();
		// Same team again, no http request
		playersCache.apply("/64");

		Assert.assertEquals(3, count[0]);

	}

	@Test
	public void testLRUCacheLimit2() {
		int[] count = {0};

		SoccerServiceAsyncNio service = new SoccerServiceAsyncNio();
		PlayersHttpSupplier playersHttpSupplier = new PlayersHttpSupplier(service);

		Function<String, String> httpWrapper = (str) -> {
			count[0]++;
			return playersHttpSupplier.apply(str);
		};

		PlayersFileSupplier playersFileSupplier = new PlayersFileSupplier(httpWrapper);
		// Cache limit 2
		PlayersCache playersCache = new PlayersCache(2, playersFileSupplier);

		// Get list of player from team with id 57
		// 1 Http Request
		playersCache.apply("/64");
		// 1 Http Requests - cache is now full
		playersCache.apply("/65");
		// 1 Http Request - remove LRU entry
		playersCache.apply("/66");
		// clean disk cache to force an http request
		cleanDiskCache();
		// Same team again but 1 Http Request is issued
		playersCache.apply("/64");

		Assert.assertEquals(4, count[0]);

	}
}