package edu.uci.ics.jindongy.service.movies.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.base.MovieModel;
import edu.uci.ics.jindongy.service.movies.model.Response.GetMovieResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMovieModel extends MovieModel {
    @JsonProperty
    private GenreModel[] genres;
    @JsonProperty
    private PersonModel[] people;

    public void setGenres(GenreModel[] genres) {
        this.genres = genres;
    }

    public void setPeople(PersonModel[] people) {
        this.people = people;
    }

    public GetMovieModel(ResultSet rs,boolean isPrivilege) throws SQLException {
        super(rs,isPrivilege);
        this.setNum_votes(rs.getInt("num_votes"));
        this.setBudget(rs.getString("budget"));
        this.setRevenue(rs.getString("revenue"));
        this.setOverview(rs.getString("overview"));


//            if(genres==null||people==null){
//                ServiceLogger.LOGGER.warning("Genres or people is null");
//            }
//            if(genres.length==0) ServiceLogger.LOGGER.warning("Not found genres");
//            if(people.length==0) ServiceLogger.LOGGER.warning("Not found people");

    }





    public class GenreModel {
        @JsonProperty
        private int genre_id;
        @JsonProperty
        private String name;

        public GenreModel(int genre_id, String name) {
            this.genre_id = genre_id;
            this.name = name;
        }
    }

}
