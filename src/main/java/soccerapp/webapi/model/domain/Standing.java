package soccerapp.webapi.model.domain;

import java.util.concurrent.CompletableFuture;

public class Standing {

	private CompletableFuture<Team> team;
	private int position;
	private String teamName;
	private int playedGames;
	private int points;
	private int goals;
	private int wins;
	private int draws;
	private int losses;
	private int teamId;

	public Standing(CompletableFuture<Team> team, int position, String teamName, int playedGames, int points,
					int goals, int wins, int draws, int losses, int teamId) {
		this.team = team;
		this.position = position;
		this.teamName = teamName;
		this.playedGames = playedGames;
		this.points = points;
		this.goals = goals;
		this.wins = wins;
		this.draws = draws;
		this.losses = losses;
		this.teamId = teamId;
	}

	public CompletableFuture<Team> getTeam() {
		return team;
	}

	public int getPosition() {
		return position;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public int getPoints() {
		return points;
	}

	public int getGoals() {
		return goals;
	}

	public int getWins() {
		return wins;
	}

	public int getDraws() {
		return draws;
	}

	public int getLosses() {
		return losses;
	}

	public int getTeamId() {
		return teamId;
	}
}
