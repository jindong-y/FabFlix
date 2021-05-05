package edu.uci.ics.jindongy.service.movies.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.base.MovieModel;
import edu.uci.ics.jindongy.service.movies.base.ResponseModel;
import edu.uci.ics.jindongy.service.movies.base.Result;

public class MovieResponse extends ResponseModel {



    @JsonProperty
    private MovieModel[] movies;

    public MovieResponse() {
    }

    public MovieResponse(MovieModel[] movies) {

        if(movies==null){
            this.setResult(Result.INTERNAL_SERVER_ERROR);
        }else if(movies.length==0){
            this.setResult(Result.NO_MOVIE_FOUND);
            this.movies=null;
        }else{
            this.setResult(Result.FOUND_MOVIE);
            this.movies = movies;

        }

    }
}
