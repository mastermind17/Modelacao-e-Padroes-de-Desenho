package soccerapp.webapi.utils.cache.leaguesTables;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;

import java.io.IOException;
import java.util.function.Function;

public class LeaguesTablesHttpSupplier implements Function<Integer, String> {

	private final SoccerServiceAsyncNio service;

	public LeaguesTablesHttpSupplier(SoccerServiceAsyncNio service){
		this.service = service;
	}

	@Override
	public String apply(Integer id) {
		try {
			return Controller.leagueTableView.apply(service.getLeagueTable(id).join());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
