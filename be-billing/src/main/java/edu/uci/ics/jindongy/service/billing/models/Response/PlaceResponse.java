package edu.uci.ics.jindongy.service.billing.models.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;

public class PlaceResponse extends ResponseModel {
    @JsonProperty
    private String approve_url;
    @JsonProperty
    private String token;

    public PlaceResponse(Results result, String approve_url, String token) {
        super(result);
        this.approve_url = approve_url;
        this.token = token;
    }
}
