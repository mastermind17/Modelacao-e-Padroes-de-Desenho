package soccerapp.webapi.model.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Team {

	private String name;
	private String code;
	private String shortName;
	private String squadMarketValue;
	private String crestUrl;
	private CompletableFuture<List<Player>> players;
	private int id;

	public Team(String name, String code, String shortName, String squadMarketValue, String crestUrl,
				CompletableFuture<List<Player>> players, int id){
		this.name = name;
		this.code = code;
		this.shortName = shortName;
		this.squadMarketValue = squadMarketValue;
		this.crestUrl = crestUrl;
		this.players = players;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getShortName() {
		return shortName;
	}

	public String getSquadMarketValue() {
		return squadMarketValue;
	}

	public String getCrestUrl() {
		return crestUrl;
	}

	public CompletableFuture<List<Player>> getPlayers() {
		return players;
	}

	public int getId() {
		return id;
	}
}
