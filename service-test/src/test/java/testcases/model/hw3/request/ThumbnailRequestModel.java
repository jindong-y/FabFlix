package testcases.model.hw3.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThumbnailRequestModel {

    @JsonProperty("movie_ids")
    String[] movie_ids;

    public ThumbnailRequestModel(String[] movie_ids) {
        this.movie_ids = movie_ids;
    }

    public String[] getMovie_ids() {
        return movie_ids;
    }
}
