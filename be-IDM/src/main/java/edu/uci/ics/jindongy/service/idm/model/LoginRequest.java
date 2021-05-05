package edu.uci.ics.jindongy.service.idm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public char[] password;

    public LoginRequest(@JsonProperty("email")
                         String email, @JsonProperty("password") char[] password) {
        this.email = email;
        this.password = password;
    }
}
