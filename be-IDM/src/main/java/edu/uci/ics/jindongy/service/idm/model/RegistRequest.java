package edu.uci.ics.jindongy.service.idm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
public class RegistRequest {

    @JsonProperty(value="email",required=true)
    public String email;
    @JsonProperty(value="password",required=true)
    public char[] password;

    public RegistRequest(@JsonProperty(value="email",required=true)
                                String email, @JsonProperty(value="password",required=true) char[] password) {
        this.email = email;
        this.password = password;
    }


}
