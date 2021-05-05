package edu.uci.ics.jindongy.service.idm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvnet.hk2.annotations.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IDMResponse {
    @JsonProperty
    int resultCode;
    @JsonProperty
    String message;
    @JsonProperty
    @Optional
    String session_id;

    public IDMResponse(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public IDMResponse(int resultCode, String message, String session_id) {
        this.resultCode = resultCode;
        this.message = message;
        this.session_id = session_id;
    }
}
