package soccerapp.webapi.app;

import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import soccerapp.webapi.model.dto.DtoLeague;
import soccerapp.webapi.model.dto.DtoLeagueTable;
import soccerapp.webapi.model.dto.DtoPlayersList;
import soccerapp.webapi.model.dto.DtoTeam;

import java.util.concurrent.CompletableFuture;

/**
 * @author Miguel Gamboa
 *         created on 23-05-2016
 */
public class SoccerWebApi implements AutoCloseable {

    private static final String HOST = "http://api.football-data.org/";
    private static final String PATH_LEAGUES = "/v1/soccerseasons";
	private static final String PATH_TABLE = "/v1/soccerseasons/%d/leagueTable";

	private static final String PATH_TEAMS = "/v1/teams/";
	private static final String PATH_PLAYERS = "/v1/players";
	private static final String PATH_TEAM_PLAYERS = "/v1/teams/%d/players";

	private static final String API_TOKEN_VALUE = "f564ad0cb00240ff9e90cfd4262bbad6";
    private static final String API_TOKEN_KEY = "X-Auth-Token";

    private final AsyncHttpClient getter;
    private final Gson gson;

    public SoccerWebApi(AsyncHttpClient getter, Gson gson)
    {
        this.getter = getter;
        this.gson = gson;
    }

    public SoccerWebApi()
    {
        this(new DefaultAsyncHttpClient(), new Gson());
    }

    public CompletableFuture<DtoLeague[]> getLeagues()
    {
        return httpGet(HOST + PATH_LEAGUES, DtoLeague[].class);
    }

    public CompletableFuture<DtoLeagueTable> getLeagueTable(int leagueId)
    {
        String path = String.format(PATH_TABLE, leagueId);
        return httpGet(HOST + path, DtoLeagueTable.class);
    }

    public CompletableFuture<DtoTeam> getTeam(String path)
    {
		return httpGet(HOST + PATH_TEAMS + path, DtoTeam.class);

//		return httpGet(path, DtoTeam.class);
    }

    public CompletableFuture<DtoPlayersList> getPlayers(String path)
    {
		return httpGet(HOST + "v1/teams" + path + "/players", DtoPlayersList.class);
//		return httpGet(path + "/players", DtoPlayersList.class);
    }

	static int count = 1;
    private <T> CompletableFuture<T> httpGet(String path, Class<T> arrayType)
    {
//		System.out.println("Pedido: " + count++ + " " + path);
		return getter
                .prepareGet(path)
                .addHeader(API_TOKEN_KEY, API_TOKEN_VALUE) // Remove comment to enable your API KEY
                .execute()
                .toCompletableFuture()
                .thenApply(r -> {
					String s = r.getResponseBody();
//					System.out.println(s);
					return gson.fromJson(s, arrayType);
				});
    }

    @Override
    public void close() throws Exception
    {
        if (!getter.isClosed()) {
            getter.close();
        }
    }

    public boolean isClosed()
    {
        return getter.isClosed();
    }
}
