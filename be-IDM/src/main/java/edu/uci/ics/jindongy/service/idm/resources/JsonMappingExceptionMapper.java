package edu.uci.ics.jindongy.service.idm.resources;

import com.fasterxml.jackson.databind.JsonMappingException;
import edu.uci.ics.jindongy.service.idm.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.idm.model.IDMResponse;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonMappingExceptionMapper implements ExtendedExceptionMapper<JsonMappingException> {

    @Override
    public boolean isMappable(JsonMappingException exception) {
        return true;
    }

    @Override
    public Response toResponse(JsonMappingException exception) {
        exception.printStackTrace();
        ServiceLogger.LOGGER.warning("MappingException");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new IDMResponse(-2,"JSON Mapping Exception."))
                .build();
    }
}
