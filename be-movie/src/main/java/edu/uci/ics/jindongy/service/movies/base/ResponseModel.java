package edu.uci.ics.jindongy.service.movies.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.movies.logger.ServiceLogger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class ResponseModel {

    @JsonIgnore
    Result result;


    @JsonProperty("resultCode")
    public int getResultCode(){
        return result.getResultCode();
    }
    @JsonProperty("message")
    public String getMessage(){
        return result.getMessage();
    }

    public ResponseModel() {
    }

    @JsonIgnore
    public Response buildResponse(HttpHeaders headers){
        ServiceLogger.LOGGER.info("Response being built with Result: " + result);
        ServiceLogger.LOGGER.info(this::getMessage);
        ServiceLogger.LOGGER.info(String.valueOf(result.getResultCode()));
        return Response.status(result.getStatus())
                .entity(this)
                .header("email",headers.getHeaderString("email"))
                .header("session_id", headers.getHeaderString("session_id"))
                .header("transaction_id",headers.getHeaderString("transaction_id"))
                .build();
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
