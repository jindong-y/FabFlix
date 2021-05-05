package edu.uci.ics.jindongy.service.billing.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONMappingExceptionMapper implements ExtendedExceptionMapper<JsonMappingException> {
    @Context
    private HttpHeaders headers;

    @Override
    public boolean isMappable(JsonMappingException exception) {
        return true;
    }

    @Override
    public javax.ws.rs.core.Response toResponse(JsonMappingException exception) {
        ServiceLogger.LOGGER.severe("JSON mapping exception");
        exception.printStackTrace();
        return new ResponseModel(Results.JSON_MAPPING_EXCEPTION).buildResponse(headers);
    }
}
