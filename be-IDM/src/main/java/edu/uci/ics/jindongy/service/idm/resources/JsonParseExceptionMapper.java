package edu.uci.ics.jindongy.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import edu.uci.ics.jindongy.service.idm.model.IDMResponse;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonParseExceptionMapper implements ExtendedExceptionMapper<JsonParseException> {

    @Override
    public boolean isMappable(JsonParseException exception) {
        return true;
    }

    @Override
    public Response toResponse(JsonParseException exception) {
        exception.printStackTrace();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new IDMResponse(-3,"JSON Parse Exception."))
                .build();
    }
}
