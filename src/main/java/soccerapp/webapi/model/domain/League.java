package soccerapp.webapi.model.domain;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class League {

	private int id;
	private String caption;
	private String league;
	private String year;
	private int currentMatchday;
	private int numberOfMatchdays;
	private int numberOfTeams;
	private int numberOfGames;
	private Date lastUpdated;
	private Function<Integer, CompletableFuture<List<Standing>>> leagueTable;

	public League(int id, String caption, String league, String year, int currentMatchday, int numberOfMatchdays,
				  int numberOfTeams, int numberOfGames, Date lastUpdated,
				  Function<Integer, CompletableFuture<List<Standing>>> leagueTable) {
		this.id = id;
		this.caption = caption;
		this.league = league;
		this.year = year;
		this.currentMatchday = currentMatchday;
		this.numberOfMatchdays = numberOfMatchdays;
		this.numberOfTeams = numberOfTeams;
		this.numberOfGames = numberOfGames;
		this.lastUpdated = lastUpdated;
		this.leagueTable = leagueTable;
	}

	public int getId() {
		return id;
	}

	public String getCaption() {
		return caption;
	}

	public String getLeague() {
		return league;
	}

	public String getYear() {
		return year;
	}

	public int getCurrentMatchday() {
		return currentMatchday;
	}

	public int getNumberOfMatchdays() {
		return numberOfMatchdays;
	}

	public int getNumberOfTeams() {
		return numberOfTeams;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public CompletableFuture<List<Standing>> getLeagueTable() {
		return leagueTable.apply(id);
	}
}
