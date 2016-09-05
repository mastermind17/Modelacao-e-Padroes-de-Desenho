import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;
import soccerapp.webapi.utils.cache.players.PlayersCache;
import soccerapp.webapi.utils.cache.players.PlayersFileSupplier;
import soccerapp.webapi.utils.cache.players.PlayersHttpSupplier;

import java.io.File;
import java.util.function.Function;

public class FileSupplierTest {

	@Before
	public void cleanDiskCache(){
		File directory = new File("src/main/resources/cache/teams/");
		for(File file: directory.listFiles()) file.delete();
	}

	@Test
	public void shouldIssueOneHttpRequestAndRetrieveFromDisk(){
		int[] httpCount = {0};
		int[] fileCount = {0};

		SoccerServiceAsyncNio service = new SoccerServiceAsyncNio();

		PlayersHttpSupplier playersHttpSupplier = new PlayersHttpSupplier(service);

		Function<String, String> httpWrapper = (str) -> {
			httpCount[0]++;
			return playersHttpSupplier.apply(str);
		};

		PlayersFileSupplier playersFileSupplier = new PlayersFileSupplier(httpWrapper);
		FileSupplierWrapper fileWrapper = new FileSupplierWrapper(fileCount, playersFileSupplier);

		PlayersCache playersCache = new PlayersCache(200, fileWrapper);

		// Get list of player from team with id 57
		// First 1 access to disk cache that is empty
		// 1 Http Request and saves to disk
		playersCache.apply("/57");

		// create a new cache with empty memory to force a disk acess
		playersCache = new PlayersCache(200, fileWrapper);
		// 0 Http Requests and 1 access to disk
		playersCache.apply("/57");

		Assert.assertEquals(1, httpCount[0]);
		Assert.assertEquals(2, fileCount[0]);
	}

	// Auxiliary class to count accesses to disk cache
	private class FileSupplierWrapper extends PlayersFileSupplier {

		int[] count;

		FileSupplierWrapper(int[] count, Function<String, String> dataSrc) {
			super(dataSrc);
			this.count = count;
		}

		@Override
		public String apply(String id){
			count[0]++;
			return super.apply(id);
		}
	}
}
