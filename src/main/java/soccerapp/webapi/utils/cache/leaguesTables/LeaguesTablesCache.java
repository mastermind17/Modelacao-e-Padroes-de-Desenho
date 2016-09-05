package soccerapp.webapi.utils.cache.leaguesTables;

import soccerapp.webapi.utils.cache.LRUCache;

import java.util.function.Function;

public class LeaguesTablesCache implements Function<Integer, String> {

	private final LRUCache<Integer, String> cache;
	private final Function<Integer, String> dataSrc;

	public LeaguesTablesCache(int limit, Function<Integer, String> dataSrc) {
		this.cache = new LRUCache<>(limit);
		this.dataSrc = dataSrc;
	}

	@Override
	public String apply(Integer id) {
		// verificar cache miss
		String html = cache.get(id);
		if(html != null)
			return html;

		html = dataSrc.apply(id);
		cache.put(id, html);
		return html;

	}
}
