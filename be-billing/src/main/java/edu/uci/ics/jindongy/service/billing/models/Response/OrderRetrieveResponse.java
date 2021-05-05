package edu.uci.ics.jindongy.service.billing.models.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import edu.uci.ics.jindongy.service.billing.models.data.transactionModel;

import java.util.List;

public class OrderRetrieveResponse extends ResponseModel {
    @JsonProperty("transactions")
    List<transactionModel> transactions;

    public OrderRetrieveResponse(Results result, List<transactionModel> transactions) {
        super(result);
        this.transactions = transactions;
    }

}
