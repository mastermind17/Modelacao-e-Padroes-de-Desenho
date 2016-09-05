package soccerapp.webapi.model.dto;

import java.util.Date;

/**
 * @author Miguel Gamboa
 *         created on 23-05-2016
 */
public class DtoLeague {
    public final int id;
    public final String caption;
    public final String league;
    public final String year;
    public final int currentMatchday;
    public final int numberOfMatchdays;
    public final int numberOfTeams;
    public final int numberOfGames;
    public final Date lastUpdated;

    public DtoLeague(
            int id,
            String caption,
            String league,
            String year,
            int currentMatchday,
            int numberOfMatchdays,
            int numberOfTeams,
            int numberOfGames,
            Date lastUpdated)
    {
        this.id = id;
        this.caption = caption;
        this.league = league;
        this.year = year;
        this.currentMatchday = currentMatchday;
        this.numberOfMatchdays = numberOfMatchdays;
        this.numberOfTeams = numberOfTeams;
        this.numberOfGames = numberOfGames;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "DtoLeague{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", league='" + league + '\'' +
                ", year='" + year + '\'' +
                ", currentMatchday=" + currentMatchday +
                ", numberOfMatchdays=" + numberOfMatchdays +
                ", numberOfTeams=" + numberOfTeams +
                ", numberOfGames=" + numberOfGames +
                ", lastUpdated=" + lastUpdated +
                "}";
    }
}
