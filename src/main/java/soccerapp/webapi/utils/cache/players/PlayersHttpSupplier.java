package soccerapp.webapi.utils.cache.players;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;

import java.io.IOException;
import java.util.function.Function;

public class PlayersHttpSupplier implements Function<String, String> {

	private final SoccerServiceAsyncNio service;

	public PlayersHttpSupplier(SoccerServiceAsyncNio service) {
		this.service = service;
	}

	@Override
	public String apply(String path) {
		try {
			return Controller.playerView.apply(service.getPlayers(path).join());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}