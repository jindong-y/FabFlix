package edu.uci.ics.jindongy.service.gateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvnet.hk2.annotations.Optional;

public class IDMSessionResponse {
    @JsonProperty
    public int resultCode;
    @JsonProperty
    public String message;
    @JsonProperty
    @Optional
    public String session_id;
}
