package edu.uci.ics.jindongy.service.gateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdmSessionRequest {
    @JsonProperty
    String email;

    @JsonProperty
    String session_id;

    public IdmSessionRequest(String email, String session_id) {
        this.email = email;
        this.session_id = session_id;
    }
}
