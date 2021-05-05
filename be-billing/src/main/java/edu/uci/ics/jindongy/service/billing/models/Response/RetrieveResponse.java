package edu.uci.ics.jindongy.service.billing.models.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import edu.uci.ics.jindongy.service.billing.models.data.itemModel;

public class RetrieveResponse extends ResponseModel {
    @JsonProperty
    private itemModel[] items;


    public RetrieveResponse(Results result, itemModel[] items) {
        super(result);
        this.items = items;
    }
}
