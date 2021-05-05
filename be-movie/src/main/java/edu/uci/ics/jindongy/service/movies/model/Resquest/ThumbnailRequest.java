package edu.uci.ics.jindongy.service.movies.model.Resquest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThumbnailRequest {
    @JsonProperty
    private String[] movie_ids;

    public String[] getMovie_ids() {
        return movie_ids;
    }
}
