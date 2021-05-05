package edu.uci.ics.cs122b.test.util;

import edu.uci.ics.cs122b.test.base.ResponseModel;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.net.URI;

public class ServiceSocket
{
    private static final Client CLIENT;

    private final URI uri;
    private final String path;
    private final MediaType requestType;
    private MultivaluedHashMap<String, Object> headers;
    private MultivaluedHashMap<String, Object> queries;

    static
    {
        CLIENT = ClientBuilder.newClient().register(JacksonFeature.class);
    }

    /**
     * Creates an instance of ServiceSocket to be used for one specific endpoint
     *
     * @param uri         URL of the target MicroService
     * @param path        path to the specific endpoint
     * @param requestType MediaType of response from the MicroService
     */
    ServiceSocket(URI uri, String path, MediaType requestType)
    {
        this.uri = uri;
        this.path = path;
        this.requestType = requestType;
        this.headers = null;
        this.queries = null;
    }

    /**
     * Calls a GET request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse get()
    {
        try (Response response = createBuilder().get())
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a GET request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> get(Class<T> responseClass)
    {
        try (Response response = createBuilder().get())
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Calls a HEAD request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse head()
    {
        try (Response response = createBuilder().head())
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a HEAD request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> head(Class<T> responseClass)
    {
        try (Response response = createBuilder().head())
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Calls a POST request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse post(Object request)
    {
        try (Response response = createBuilder().post(Entity.entity(request, requestType)))
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a POST request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> post(Class<T> responseClass, Object request)
    {
        try (Response response = createBuilder().post(Entity.entity(request, requestType)))
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Calls a PUT request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse put(Object request)
    {
        try (Response response = createBuilder().put(Entity.entity(request, requestType)))
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a PUT request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> put(Class<T> responseClass, Object request)
    {
        try (Response response = createBuilder().put(Entity.entity(request, requestType)))
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Calls a DELETE request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse delete()
    {
        try (Response response = createBuilder().delete())
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a DELETE request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> delete(Class<T> responseClass)
    {
        try (Response response = createBuilder().delete())
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Calls a OPTIONS request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse options()
    {
        try (Response response = createBuilder().options())
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a OPTIONS request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> options(Class<T> responseClass)
    {
        try (Response response = createBuilder().options())
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Calls a TRACE request
     *
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public ServiceResponse trace()
    {
        try (Response response = createBuilder().trace())
        {
            return ServiceResponse.buildResponse(response);
        }
    }

    /**
     * Calls a TRACE request
     *
     * @param responseClass the model class that will be used to map the entity
     * @param <T>           model class that extends {@link ResponseModel}
     * @return an instance of {@link ServiceResponse} that holds the
     *         entity and response details
     */
    public <T extends ResponseModel> ServiceResponse<T> trace(Class<T> responseClass)
    {
        try (Response response = createBuilder().trace())
        {
            return ServiceResponse.buildResponse(response, responseClass);
        }
    }

    /**
     * Adds the headers to the socket call
     *
     * @param headers a MultivaluedHashMap instance
     * @return the object being called
     */
    public ServiceSocket headers(MultivaluedHashMap<String, Object> headers)
    {
        this.headers = headers;
        return this;
    }

    /**
     * Adds the headers to the socket call
     *
     * @param queries a MultivaluedHashMap instance
     * @return the object being called
     */
    public ServiceSocket queries(MultivaluedHashMap<String, Object> queries)
    {
        this.queries = queries;
        return this;
    }

    /**
     * Internal builder creator, uses the instances path, uri, queries and header to
     * construct a {@link WebTarget} to instantiate a {@link Builder}
     *
     * @return an instance of {@link Builder} to be used to query the MicroService
     */
//    private Builder createBuilder()
//    {
//        WebTarget webTarget = CLIENT.target(uri).path(path);
//
//        if (queries != null)
//            for (String key : queries.keySet())
//                webTarget = webTarget.queryParam(key, queries.getFirst(key));
//
//        if (headers != null)
//            return webTarget.request(requestType).headers(headers);
//        else
//            return webTarget.request(requestType);
//    }

    private Builder createBuilder()
    {
        WebTarget webTarget = CLIENT.target(uri).path(path);

        if (queries != null)
            for (String key : queries.keySet())
                webTarget = webTarget.queryParam(key, queries.getFirst(key));

        Builder builder =  webTarget.request(requestType);

        if (headers != null)
            for (String key : headers.keySet())
                builder.header(key, headers.getFirst(key));

        return builder;
    }
}
