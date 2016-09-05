package soccerapp.webapi.model.domain.async;

import soccerapp.webapi.app.Controller;
import soccerapp.webapi.app.SoccerWebApi;
import soccerapp.webapi.model.domain.League;
import soccerapp.webapi.model.domain.Player;
import soccerapp.webapi.model.domain.Standing;
import soccerapp.webapi.model.domain.Team;
import soccerapp.webapi.model.dto.DtoLeague;
import soccerapp.webapi.model.dto.DtoLeagueTableStanding;
import soccerapp.webapi.model.dto.DtoPlayer;
import soccerapp.webapi.model.dto.DtoTeam;
import soccerapp.webapi.services.http.HttptUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SoccerServiceAsyncNio implements AutoCloseable {

	private final SoccerWebApi api = new SoccerWebApi();

	public Stream<League> getLeagues(){
		CompletableFuture<Stream<League>> promise = api
				.getLeagues()
				.thenApply(Arrays::stream)
				.thenApply(stream -> stream.map(this::dtoToDomain));
		return Stream
				.of(promise)
				.flatMap(CompletableFuture::join);
	}

	private League dtoToDomain(DtoLeague dto){
		return new League(
				dto.id,
				dto.caption,
				dto.league,
				dto.year,
				dto.currentMatchday,
				dto.numberOfMatchdays,
				dto.numberOfTeams,
				dto.numberOfGames,
				dto.lastUpdated,
				this::getLeagueTable
		);
	}

	public CompletableFuture<List<Standing>> getLeagueTable(int id){
		return api.getLeagueTable(id)
				.thenApply(dto -> Arrays.stream(dto.standing))
				.thenApply(stream -> stream.map(this::dtoToDomain))
				.thenApply(stream -> stream.collect(toList()));
	}

	private Standing dtoToDomain(DtoLeagueTableStanding dto){
		String id = String.valueOf(HttptUtils.getLastSegment(dto._links.team.href));

		CompletableFuture<Team> promise = getTeam(id);
		Controller.teamsCache.save(dto._links.team.href, promise);

		return new Standing(
				promise,
				dto.position,
				dto.teamName,
				dto.playedGames,
				dto.points,
				dto.goals,
				dto.wins,
				dto.draws,
				dto.losses,
				HttptUtils.getLastSegment(dto._links.team.href)
		);
	}

	public CompletableFuture<Team> getTeam(String path){
		String id = String.valueOf(HttptUtils.getLastSegment(path));
		return api.getTeam(id)
				.thenApply(this::dtoToDomain);
	}

	private Team dtoToDomain(DtoTeam dto){
		String href = dto._links.players.href;
		int teamId = HttptUtils.getLastSegment(href.substring(0, href.lastIndexOf("/")));
		CompletableFuture<List<Player>> promise = getPlayers("/" + teamId);
		Controller.playersCache.save(String.valueOf(teamId), promise);
		return new Team(
				dto.name,
				dto.code,
				dto.shortName,
				dto.squadMarketValue,
				dto.crestUrl,
				promise,
				teamId
		);
	}

	public CompletableFuture<List<Player>> getPlayers(String path){
		return api.getPlayers(path)
				.thenApply(dto -> Arrays.stream(dto.players))
				.thenApply(stream -> stream.map(this::dtoToDomain))
				.thenApply(stream -> stream.collect(toList()));
	}

	private Player dtoToDomain(DtoPlayer dto){
		return new Player(
				dto.name,
				dto.position,
				dto.jerseyNumber,
				dto.dateOfBirth,
				dto.nationality,
				dto.contractUntil,
				dto.marketValue
		);
	}

	@Override
	public void close() throws Exception {
		if(!api.isClosed()) api.close();
	}

	public boolean isClosed() {
		return api.isClosed();
	}
}
