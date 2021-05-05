package edu.uci.ics.jindongy.service.billing.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class ResponseModel {
    @JsonProperty("resultCode")
    private int resultCode;
    public int getResultCode(){
        return result.getResultCode();
    }

    @JsonProperty("message")
    private String message;
    public String getMessage(){
        return result.getMessage();
    }

    @JsonIgnore
    Results result;

    public ResponseModel() {
    }

    public ResponseModel(Results result){
        this.result=result;
        this.resultCode=result.getResultCode();
        this.message=result.getMessage();
    }

    public Response buildResponse(HttpHeaders headers){
        ServiceLogger.LOGGER.info("Response being built with Result: " + result);
        ServiceLogger.LOGGER.info(getMessage());
        ServiceLogger.LOGGER.info(String.valueOf(result.getResultCode()));
        try {

            return Response.status(result.getStatus())
                    .entity(this)
                    .header("email", headers.getHeaderString("email"))
                    .header("session_id", headers.getHeaderString("session_id"))
                    .header("transaction_id", headers.getHeaderString("transaction_id"))
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(result.getStatus()).entity(this).build();
        }
    }

    public void setResult(Results result) {
        this.result = result;
    }
}
