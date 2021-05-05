package edu.uci.ics.jindongy.service.idm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrivilegeRequest {
    @JsonProperty
    public String email;
    @JsonProperty
    public int plevel;
}
