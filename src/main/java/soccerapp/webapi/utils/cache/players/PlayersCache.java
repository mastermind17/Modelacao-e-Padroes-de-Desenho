package soccerapp.webapi.utils.cache.players;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.model.domain.Player;
import soccerapp.webapi.utils.cache.LRUCache;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class PlayersCache implements Function<String, String> {

	private final LRUCache<String, String> cache;
	private final PlayersFileSupplier dataSrc;

	public PlayersCache(int limit, PlayersFileSupplier dataSrc) {
		this.cache = new LRUCache<>(limit);
		this.dataSrc = dataSrc;
	}

	@Override
	public String apply(String path) {
		// verificar cache miss
		String html = cache.get(path.substring(1));
		if(html != null)
			return html;

		html = dataSrc.apply(path);
		cache.put(path.substring(1), html);
		return html;

	}

	public void save(String key, CompletableFuture<List<Player>> promise){
		promise.thenAccept(players -> {
			try {
				String html = Controller.playerView.apply(players);
				cache.put(key, html);
				dataSrc.writeToDisk(key, html);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
