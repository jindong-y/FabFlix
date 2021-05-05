package edu.uci.ics.jindongy.service.billing.models.Request;


import com.fasterxml.jackson.annotation.JsonProperty;

public class IDMRequest {
    @JsonProperty
    private String email;
    @JsonProperty
    private Integer plevel;

    public IDMRequest(String email, int plevel) {
        this.email = email;
        this.plevel = plevel;
    }
}
