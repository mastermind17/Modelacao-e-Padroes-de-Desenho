package soccerapp.webapi.utils.cache.teams;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.model.domain.Team;
import soccerapp.webapi.services.http.HttptUtils;
import soccerapp.webapi.utils.cache.LRUCache;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class TeamsCache implements Function<String, String> {

	private final LRUCache<String, String> cache;
	private final TeamsFileSupplier dataSrc;

	public TeamsCache(int limit, TeamsFileSupplier dataSrc) {
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

	public void save(String href, CompletableFuture<Team> promise) {
		promise.thenAccept(team -> {
			String key = String.valueOf(HttptUtils.getLastSegment(href));
			try {
				String html = Controller.teamView.apply(team);
				cache.put(key.substring(1), html);
				dataSrc.writeToDisk(key, html);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}
}
