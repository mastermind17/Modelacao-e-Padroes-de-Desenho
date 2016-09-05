package soccerapp.webapi.app;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;
import soccerapp.webapi.services.http.HttptUtils;
import soccerapp.webapi.utils.cache.leaguesList.LeaguesListCache;
import soccerapp.webapi.utils.cache.leaguesList.LeaguesListFileSupplier;
import soccerapp.webapi.utils.cache.leaguesList.LeaguesListHttpSupplier;
import soccerapp.webapi.utils.cache.leaguesTables.LeaguesTablesCache;
import soccerapp.webapi.utils.cache.leaguesTables.LeaguesTablesFileSupplier;
import soccerapp.webapi.utils.cache.leaguesTables.LeaguesTablesHttpSupplier;
import soccerapp.webapi.utils.cache.players.PlayersCache;
import soccerapp.webapi.utils.cache.players.PlayersFileSupplier;
import soccerapp.webapi.utils.cache.players.PlayersHttpSupplier;
import soccerapp.webapi.utils.cache.teams.TeamsCache;
import soccerapp.webapi.utils.cache.teams.TeamsFileSupplier;
import soccerapp.webapi.utils.cache.teams.TeamsHttpSupplier;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Controller implements AutoCloseable {

	public static final Template mainView, leaguesView, leagueTableView, playerView, teamView, head, header, footer;
	private final SoccerServiceAsyncNio service;

	static { // Construtor de classe
		try {
			TemplateLoader loader = new ClassPathTemplateLoader();
			loader.setPrefix("/views");
			Handlebars handlebars = new Handlebars(loader);
			mainView = handlebars.compile("main");
			leaguesView = handlebars.compile("leagues");
			leagueTableView = handlebars.compile("leagueTable");
			playerView = handlebars.compile("player");
			teamView = handlebars.compile("team");
			head = handlebars.compile("common/head");
			header = handlebars.compile("common/header");
			footer = handlebars.compile("common/footer");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static LeaguesListCache leaguesListCache;
	public static LeaguesTablesCache leaguesTablesCache;
	public static TeamsCache teamsCache;
	public static PlayersCache playersCache;

	public Controller(SoccerServiceAsyncNio service) {
		this.service = service;
		// leagues list cache
		LeaguesListHttpSupplier leaguesListHttpSupplier = new LeaguesListHttpSupplier(service);
		LeaguesListFileSupplier leaguesListFileSupplier = new LeaguesListFileSupplier(leaguesListHttpSupplier);
		leaguesListCache = new LeaguesListCache(leaguesListFileSupplier);
		// leagues tables cache
		LeaguesTablesHttpSupplier leaguesTablesHttpSupplier = new LeaguesTablesHttpSupplier(service);
		LeaguesTablesFileSupplier leaguesTablesFileSupplier = new LeaguesTablesFileSupplier(leaguesTablesHttpSupplier);
		leaguesTablesCache = new LeaguesTablesCache(20, leaguesTablesFileSupplier);
		// teams cache
		TeamsHttpSupplier teamsHttpSupplier = new TeamsHttpSupplier(service);
		TeamsFileSupplier teamsFileSupplier = new TeamsFileSupplier(teamsHttpSupplier);
		teamsCache = new TeamsCache(100, teamsFileSupplier);
		// players cache
		PlayersHttpSupplier playersHttpSupplier = new PlayersHttpSupplier(service);
		PlayersFileSupplier playersFileSupplier = new PlayersFileSupplier(playersHttpSupplier);
		playersCache = new PlayersCache(200, playersFileSupplier);
	}

	public String getLeagues(HttpServletRequest req) throws IOException {
		return leaguesListCache.get();
	}

	public String getLeagueTable(HttpServletRequest req) throws IOException {
		int id = HttptUtils.getLastSegment(req);
		return leaguesTablesCache.apply(id);
	}

	public String getTeam(HttpServletRequest req) throws IOException {
		return teamsCache.apply(req.getPathInfo());
	}

	public String getPlayers(HttpServletRequest req) throws IOException {
		return playersCache.apply(req.getPathInfo());
	}

	@Override
	public void close() throws Exception {
		if(!service.isClosed())
			service.close();
	}
}
