package soccerapp.webapi.app;

import soccerapp.webapi.model.domain.async.SoccerServiceAsyncNio;
import soccerapp.webapi.services.http.HttpServer;

/**
 * @author Miguel Gamboa
 *         created on 23-05-2016
 */
public class Program {

	public static void main(String[] args){

		try(Controller controller = new Controller(new SoccerServiceAsyncNio())) {
			new HttpServer(3000)
					.addHandler("/soccerapp/leagues", controller::getLeagues)
					.addHandler("/soccerapp/leagues/*", controller::getLeagueTable)
					.addHandler("/soccerapp/teams/*", controller::getTeam)
					.addHandler("/soccerapp/players/*", controller::getPlayers)
					.run();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
