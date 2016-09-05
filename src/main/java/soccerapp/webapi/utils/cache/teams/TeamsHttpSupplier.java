package soccerapp.webapi.utils.cache.teams;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;

import java.io.IOException;
import java.util.function.Function;

public class TeamsHttpSupplier implements Function<String, String> {

	private final SoccerServiceAsyncNio service;

	public TeamsHttpSupplier(SoccerServiceAsyncNio service){
		this.service = service;
	}

	@Override
	public String apply(String path) {
		try {
			return Controller.teamView.apply(service.getTeam(path).join());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
