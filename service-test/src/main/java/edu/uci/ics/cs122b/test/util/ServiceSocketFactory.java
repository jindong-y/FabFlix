package edu.uci.ics.cs122b.test.util;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class ServiceSocketFactory
{
    private final URI uri;
    private final MediaType requestType = MediaType.APPLICATION_JSON_TYPE;

    /**
     * Creates an instance of ServiceSocketFactory
     *
     * @param uri URL of the target MicroService
     */
    private ServiceSocketFactory(URI uri)
    {
        this.uri = uri;
    }

    /**
     * Creates an instance of a ServiceSocket using path and queries
     *
     * @param path path of the target endpoint
     * @return an instance of ServiceSocket
     */
    public ServiceSocket createSocket(String path)
    {
        return new ServiceSocket(uri, path, requestType);
    }

    /**
     * Creates and instance of ServiceSocketFactory
     *
     * @param uri the string URL of the target MicroService
     * @return an instance of ServiceSocketFactory
     */
    public static ServiceSocketFactory createFactory(URI uri)
    {
        return new ServiceSocketFactory(uri);
    }

    /**
     * Creates and instance of ServiceSocketFactory, converting URL string into {@link URI}
     *
     * @param uri the string URL of the target MicroService
     * @return an instance of ServiceSocketFactory
     */
    public static ServiceSocketFactory createFactory(String uri)
    {
        return new ServiceSocketFactory(URI.create(uri));
    }
}
