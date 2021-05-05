package edu.uci.ics.jindongy.service.gateway.resources;


import edu.uci.ics.jindongy.service.gateway.GatewayService;
import edu.uci.ics.jindongy.service.gateway.configs.Configs;
import edu.uci.ics.jindongy.service.gateway.configs.IdmConfigs;
import edu.uci.ics.jindongy.service.gateway.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.gateway.model.IDMSessionResponse;
import edu.uci.ics.jindongy.service.gateway.model.IdmSessionRequest;
import edu.uci.ics.jindongy.service.gateway.threadpool.ClientRequest;
import edu.uci.ics.jindongy.service.gateway.threadpool.HTTPMethod;
import edu.uci.ics.jindongy.service.gateway.threadpool.ThreadPool;
import edu.uci.ics.jindongy.service.gateway.transaction.TransactionGenerator;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("")
public class GatewayResource {

    @Context
    private HttpHeaders headers;

    @Context
    private Request request;

    @Context
    private UriInfo uri;
    /**
     * build request and put it into the queue. And build response
     *
     * @param jsonBytes
     * @return Response
     */
    private Response buildGatewayResponse(byte[] jsonBytes,String service,String endpoint) {
        ServiceLogger.LOGGER.info("Build request");
        //generate transaction id
        String transaction_id = TransactionGenerator.generate();
        Object config;
        switch (service){
            case "idm":
                config=GatewayService.getIdmConfigs();
                break;
            case "billing":
                config=GatewayService.getBillingConfigs();
                break;
            case "movies":
                config=GatewayService.getMoviesConfigs();
                break;
            default:
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No such endpoint").build();
        }


        //get url
        String url=requestUrlBuilder(config);
        //get endpoint url

        ServiceLogger.LOGGER.info(uri.getRequestUri().toString());

        //build request
        ClientRequest clientRequest = new ClientRequest(headers, transaction_id, url, endpoint, request.getMethod(), jsonBytes);
        //put the request into the queue
        GatewayService.getThreadPool().putRequest(clientRequest);

        //return status 204
        return Response.status(Status.NO_CONTENT)
                .header("massage", "Ni Hao, come back later")
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay())
                .header("transaction_id", transaction_id)
                .build();
    }
    private  String requestUrlBuilder(Object config) {

        String uri= null;
        try {
            Class configClass=config.getClass();
            configClass.getMethod("getScheme");
            Method m=configClass.getMethod("getScheme");
            uri = UriBuilder.fromUri(
                   (String) configClass.getMethod("getScheme").invoke(config)
                           + configClass.getMethod("getHostName").invoke(config)
                           + configClass.getMethod("getPath").invoke(config))
                   .port((int) configClass.getMethod("getPort").invoke(config))
                   .build().toString();
        } catch (Exception e){
            e.printStackTrace();
            ServiceLogger.LOGGER.severe("Url Builder error");
        }

        return uri;

    }

    private Response sessionVerify() {
        ServiceLogger.LOGGER.info("Verify session....");
        try {

            URI uri;
            IdmConfigs config = GatewayService.getIdmConfigs();

            IdmSessionRequest request = new IdmSessionRequest(headers.getHeaderString("email"),
                    headers.getHeaderString("session_id"));
            uri = UriBuilder.fromUri(config.getScheme() + config.getHostName() + config.getPath()).port(config.getPort()).build();
            ServiceLogger.LOGGER.info("Build uri: " + uri.toString());


            // Create a new Client
            ServiceLogger.LOGGER.info("Building client...");
            Client client = ClientBuilder.newClient();
            client.register(JacksonFeature.class);

            ServiceLogger.LOGGER.info("Building WebTarget...");
            WebTarget webTarget = client.target(uri.toString()).path("session");

            // Create an InvocationBuilder to create the HTTP request
            ServiceLogger.LOGGER.info("Starting invocation builder...");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

            // Send the request and save it to a Response
            ServiceLogger.LOGGER.info("Sending request...");
            ServiceLogger.LOGGER.info(Entity.entity(request, MediaType.APPLICATION_JSON).toString());
            Response response = invocationBuilder
                    .header("email", headers.getHeaderString("email"))
                    .header("session_id", headers.getHeaderString("session_id"))
                    .header("transaction_id", headers.getHeaderString("transaction_id"))
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON));
            ServiceLogger.LOGGER.info("Request sent.");

            ServiceLogger.LOGGER.info("Received status " + response.getStatus());


            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("")
    public String hello() throws Exception {
        ServiceLogger.LOGGER.info("hello");
        ServiceLogger.LOGGER.info(requestUrlBuilder(GatewayService.getBillingConfigs()));
        return "你好";
    }

    @POST
    @Path("test/{x:.+}")
    public String test(@QueryParam("new") String para,
                       @PathParam("x")String x) {
        ServiceLogger.LOGGER.info(para);
        ServiceLogger.LOGGER.info(x);


        String endpointurl=uri.getRequestUri().toString().substring(uri.getBaseUri().toString().length());
//        ServiceLogger.LOGGER.info(params);
        ServiceLogger.LOGGER.info(uri.getRequestUri().toString());
        ServiceLogger.LOGGER.info(uri.getBaseUri().toString());
        ServiceLogger.LOGGER.info(uri.getAbsolutePath().toString());
        ServiceLogger.LOGGER.info(uri.getPath());
        return "test";
    }

    @POST
    @Path("{service}/{endpoint:.+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(byte[] jsonBytes,
                         @PathParam("service")String service,
                         @PathParam("endpoint")String endpoint){
        ServiceLogger.LOGGER.info("service:"+service);

        endpoint+=uri.getRequestUri().toString().substring(uri.getAbsolutePath().toString().length());
        ServiceLogger.LOGGER.info("endpoint:"+endpoint);

        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
        if(!service.equals("idm")) {
            Response idmResponse = sessionVerify();
            IDMSessionResponse payload = idmResponse.readEntity(IDMSessionResponse.class);
            if (payload.resultCode != 130) {
                ServiceLogger.LOGGER.info("session is not active");
                return idmResponse;
            }
        }

        return buildGatewayResponse(jsonBytes,service,endpoint);

    }


    @GET
    @Path("{service}/{endpoint:.+}")
    public Response get(@PathParam("service")String service,
                         @PathParam("endpoint")String endpoint){
        ServiceLogger.LOGGER.info("service:"+service);
        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
        endpoint+=uri.getRequestUri().toString().substring(uri.getAbsolutePath().toString().length());
        ServiceLogger.LOGGER.info("endpoint:"+endpoint);

            Response idmResponse = sessionVerify();
            IDMSessionResponse payload = idmResponse.readEntity(IDMSessionResponse.class);
            if (payload.resultCode != 130) {
                ServiceLogger.LOGGER.info("session is not active");
                return idmResponse;
            }
        return buildGatewayResponse(null,service,endpoint);

    }
//
//
//
//    //////////--------------------------IDM----------------------------------
//    @POST
//    @Path("idm/{endpoint:(register|login|session|privilege)}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response idm(byte[] jsonBytes) {
//        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
//        return buildGatewayResponse(jsonBytes,GatewayService.getIdmConfigs(),"idm");
//    }
//
//
//
//    //////////--------------------------MOVIES----------------------------------
//    @GET
//    @Path("movies/{x:search|(browse/.*)|(get/.*)|people|(people/search)|(people/get/.*)}")
//     public Response movies() {
//        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
//
//        Response idmResponse =sessionVerify();
//        IDMSessionResponse payload = idmResponse.readEntity(IDMSessionResponse.class);
//        if(payload.resultCode!=130){
//            ServiceLogger.LOGGER.info("session is not active");
//            return idmResponse;
//        }
//
//        return buildGatewayResponse(null,GatewayService.getMoviesConfigs());
//    }
//
//    @POST
//    @Path("movies/thumbnail")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response thumnail(byte[] jsonBytes){
//        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
//
//        Response idmResponse =sessionVerify();
//        IDMSessionResponse payload = idmResponse.readEntity(IDMSessionResponse.class);
//        if(payload.resultCode!=130){
//            ServiceLogger.LOGGER.info("session is not active");
//            return idmResponse;
//        }
//        return buildGatewayResponse(jsonBytes,GatewayService.getMoviesConfigs());
//    }
//
//
//    //////////--------------------------BILLING----------------------------------
//
//    @POST
//    @Path("billing/{x:(cart/(insert|update|delete|retrieve|clear))|(order/(place|retrieve))}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response billing(byte[] jsonBytes){
//        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
//
//        Response idmResponse =sessionVerify();
//        IDMSessionResponse payload = idmResponse.readEntity(IDMSessionResponse.class);
//        if(payload.resultCode!=130){
//            ServiceLogger.LOGGER.info("session is not active");
//            return idmResponse;
//        }
//
//        return buildGatewayResponse(jsonBytes,GatewayService.getBillingConfigs());
//    }
//
//    @GET
//    @Path("billing/order/complete")
//    public Response orderComplete(){
//        ServiceLogger.LOGGER.info(uri.getPath()+" is called");
//        return buildGatewayResponse(null,GatewayService.getBillingConfigs());
//
//    }

    //////////--------------------------GATEWAY----------------------------------
    @GET
    @Path("report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response report(){
        ServiceLogger.LOGGER.info(uri.getPath()+" is called");

//        Response idmResponse =sessionVerify();
//        IDMSessionResponse payload = idmResponse.readEntity(IDMSessionResponse.class);
//        if(payload.resultCode!=130){
//            ServiceLogger.LOGGER.info("session is not active");
//            return idmResponse;
//        }

        ServiceLogger.LOGGER.info("Try get report");

        Connection con= null;
        try {
            con = GatewayService.getConnectionPoolManager().requestCon();
        } catch (SQLException throwables) {
            ServiceLogger.LOGGER.severe(throwables.toString());
            ServiceLogger.LOGGER.severe("Get connection error");
        }
        try {
            PreparedStatement ps=con.prepareStatement(
                    "SELECT transaction_id, email, session_id, response, http_status\n" +
                            "FROM responses\n" +
                            "WHERE transaction_id= ?;"
            );
            ps.setString(1,headers.getHeaderString("transaction_id"));
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){
                GatewayService.getConnectionPoolManager().releaseCon(con);
                ServiceLogger.LOGGER.warning("Response is not ready");
                return Response.status(Status.NO_CONTENT)
                        .header("massage", "Come back later")
                        .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay())
                        .header("transaction_id", headers.getHeaderString("transaction_id"))
                        .build();
            }
            int status=rs.getInt("http_status");
            String entity=rs.getString("response");
            ServiceLogger.LOGGER.info("Get response and is deleting it from DB");
            ps=con.prepareStatement(
                    "DELETE  FROM responses\n" +
                            "WHERE transaction_id=?;"
            );
            ps.setString(1,headers.getHeaderString("transaction_id"));
            if(ps.executeUpdate()!=1){
                GatewayService.getConnectionPoolManager().releaseCon(con);
                ServiceLogger.LOGGER.warning("Response delete error");
             return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
            GatewayService.getConnectionPoolManager().releaseCon(con);

            ServiceLogger.LOGGER.warning("response is sent back ");
            ServiceLogger.LOGGER.info("entity: "+entity);
            ServiceLogger.LOGGER.info("status: "+status);
            return Response.status(Status.OK).entity(entity)
                    .header("transaction_id", headers.getHeaderString("transaction_id"))
                    .build();



        } catch (SQLException throwables) {
            ServiceLogger.LOGGER.severe(throwables.toString());
            ServiceLogger.LOGGER.severe("SQL error");
            GatewayService.getConnectionPoolManager().releaseCon(con);

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("SQL error").build();
        }



    }

}

