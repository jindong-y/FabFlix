package edu.uci.ics.jindongy.service.billing.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThumbnailRequest {
    @JsonProperty
    private String[] movie_ids;

    public String[] getMovie_ids() {
        return movie_ids;
    }

    public ThumbnailRequest(String[] movie_ids) {
        this.movie_ids = movie_ids;
    }
}