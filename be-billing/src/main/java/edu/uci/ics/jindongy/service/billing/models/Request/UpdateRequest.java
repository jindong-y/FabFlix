package edu.uci.ics.jindongy.service.billing.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRequest {
    @JsonProperty
    public String email;
    @JsonProperty
    public String movie_id;

    @JsonProperty
    public Integer quantity;
}
