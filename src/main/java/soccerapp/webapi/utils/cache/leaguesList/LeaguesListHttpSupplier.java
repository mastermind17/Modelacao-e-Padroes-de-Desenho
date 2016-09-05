package soccerapp.webapi.utils.cache.leaguesList;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.model.domain.League;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class LeaguesListHttpSupplier implements Supplier<String> {

	private final SoccerServiceAsyncNio service;

	public LeaguesListHttpSupplier(SoccerServiceAsyncNio service){
		this.service = service;
	}

	@Override
	public String get() {
		try {
			List<League> model = service.getLeagues().collect(toList());
			return Controller.leaguesView.apply(model);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
