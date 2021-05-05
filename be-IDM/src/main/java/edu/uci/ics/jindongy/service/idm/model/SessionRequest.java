package edu.uci.ics.jindongy.service.idm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionRequest {
    @JsonProperty
    public String email;
    @JsonProperty
    public String session_id;

}
