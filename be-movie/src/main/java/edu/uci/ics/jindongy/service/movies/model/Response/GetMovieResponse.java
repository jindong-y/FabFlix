package edu.uci.ics.jindongy.service.movies.model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.base.ResponseModel;
import edu.uci.ics.jindongy.service.movies.base.Result;
import edu.uci.ics.jindongy.service.movies.model.data.GetMovieModel;

public class GetMovieResponse extends ResponseModel {
    @JsonProperty
    private GetMovieModel movie;

    public GetMovieResponse(GetMovieModel movie) {
        if(movie==null){
            this.setResult(Result.NO_MOVIE_FOUND);
        }else {
            this.setResult(Result.FOUND_MOVIE);
        }
        this.movie = movie;
    }


}
