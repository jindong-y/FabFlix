package edu.uci.ics.jindongy.service.movies.model.Resquest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrivilegeRequest {
    @JsonProperty
    public String email;
    @JsonProperty
    public int plevel;

    public PrivilegeRequest(String email, int plevel) {
        this.email = email;
        this.plevel = plevel;
    }
}
