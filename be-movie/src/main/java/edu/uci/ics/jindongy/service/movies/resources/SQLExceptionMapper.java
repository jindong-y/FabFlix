package edu.uci.ics.jindongy.service.movies.resources;

import edu.uci.ics.jindongy.service.movies.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.movies.model.Response.MovieResponse;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
@Provider

public class SQLExceptionMapper implements ExtendedExceptionMapper<SQLException> {
    @Override
    public boolean isMappable(SQLException exception) {
        return true;
    }

    @Override
    public Response toResponse(SQLException exception) {
        ServiceLogger.LOGGER.severe("SQL exception");
        exception.printStackTrace();
        return new MovieResponse(null).buildResponse(MovieResource.getHeaders());

    }
}

