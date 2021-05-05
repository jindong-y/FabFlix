package edu.uci.ics.jindongy.service.billing.resources;

import com.fasterxml.jackson.core.JsonParseException;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONParseExceptionMapper implements ExtendedExceptionMapper<JsonParseException> {
    @Context
    private HttpHeaders headers;
    @Override
    public boolean isMappable(JsonParseException exception) {
        return true;
    }

    @Override
    public javax.ws.rs.core.Response toResponse(JsonParseException exception) {
        ServiceLogger.LOGGER.severe("JSON parse exception");
        exception.printStackTrace();
        return new ResponseModel(Results.JSON_PARSE_EXCEPTION).buildResponse(headers);
    }
}
