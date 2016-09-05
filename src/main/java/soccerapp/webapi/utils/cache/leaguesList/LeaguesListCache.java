package soccerapp.webapi.utils.cache.leaguesList;

import java.util.function.Supplier;

public class LeaguesListCache implements Supplier<String> {

	private String cache;
	private final Supplier<String> dataSrc;

	public LeaguesListCache(Supplier<String> dataSrc) {
		this.dataSrc = dataSrc;
	}

	@Override
	public String get() {
		// verificar cache miss
		if(cache != null)
			return cache;

		cache = dataSrc.get();
		return cache;
	}
}