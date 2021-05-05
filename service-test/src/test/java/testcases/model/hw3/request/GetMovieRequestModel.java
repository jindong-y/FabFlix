package testcases.model.hw3.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMovieRequestModel {

    @JsonProperty("movie_id")
    String movie_id;

    public GetMovieRequestModel(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_id() {
        return movie_id;
    }
}
