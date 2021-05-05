package edu.uci.ics.jindongy.service.gateway.threadpool;

import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements ExtendedExceptionMapper<Throwable> {

    @Override
    public boolean isMappable(Throwable exception) {
        return false;
    }

    @Override
    public Response toResponse(Throwable exception) {

        return null;
    }
}
