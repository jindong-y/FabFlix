package edu.uci.ics.jindongy.service.billing.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.base.ResponseModel;
import edu.uci.ics.jindongy.service.billing.models.base.Results;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.sql.SQLException;

@Provider
public class ExceptionMapper implements ExtendedExceptionMapper<Exception> {
    @Context
    private HttpHeaders headers;
    @Context
    private UriInfo uri;

    @Override
    public boolean isMappable(Exception throwable) {
        return true;
    }

    @Override
    public javax.ws.rs.core.Response toResponse(Exception throwable) {

        throwable.printStackTrace();
        ServiceLogger.LOGGER.severe(throwable.getMessage());

        if (throwable instanceof IOException) {
            ServiceLogger.LOGGER.severe("Capture order failed\n");
            return new ResponseModel(Results.ORDER_CREATE_FAILED).buildResponse(headers);
        } else if (throwable instanceof SQLException) {
            if (uri.getPath().equals("order/place")) {
                return new ResponseModel(Results.ORDER_CREATE_FAILED).buildResponse(headers);
            }
            return new ResponseModel(Results.CART_OPERATION_FAILED).buildResponse(headers);
        }
        return new ResponseModel(Results.INTERNAL_SERVER_ERROR).buildResponse(headers);
    }


}
