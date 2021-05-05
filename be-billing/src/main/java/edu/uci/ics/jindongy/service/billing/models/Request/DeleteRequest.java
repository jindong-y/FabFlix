package edu.uci.ics.jindongy.service.billing.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest {
    @JsonProperty

    public String movie_id;

    @JsonProperty

    public String email;
}
