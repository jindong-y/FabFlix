package edu.uci.ics.jindongy.service.gateway.threadpool;


import edu.uci.ics.jindongy.service.gateway.GatewayService;
import edu.uci.ics.jindongy.service.gateway.connectionpool.ConnectionPoolManager;
import edu.uci.ics.jindongy.service.gateway.logger.ServiceLogger;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Worker extends Thread {
    int id;
    ThreadPool threadPool;

    private Worker(int id, ThreadPool threadPool) {
        this.id = id;
        this.threadPool = threadPool;
    }

    public static Worker CreateWorker(int id, ThreadPool threadPool) {

        return new Worker(id, threadPool);
    }

    public void process(ClientRequest request)  {

        



        // Create a new Client
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);
        WebTarget webTarget = client.target(request.getURI()+"/"+request.getEndpoint());
        // Create an InvocationBuilder to create the HTTP request
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        // Send the request and save it to a Response
        ServiceLogger.LOGGER.info("Sending request...");
        ServiceLogger.LOGGER.info("To :"+request.getURI()+"/"+request.getEndpoint());
        Response response = null;
        try {
             response = invocationBuilder
                        .header("email", request.getEmail())
                        .header("session_id", request.getSession_id())
                        .header("transaction_id", request.getTransaction_id())
                        .method(request.getMethod().toString(),Entity.entity(request.getRequestBytes(), MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            ServiceLogger.LOGGER.severe(e.toString());
            ServiceLogger.LOGGER.severe("Request sending error");
        }
        //Insert response;


        //Get connection
        Connection con= null;
        try {
            con = GatewayService.getConnectionPoolManager().requestCon();
        } catch (SQLException throwables) {
            ServiceLogger.LOGGER.severe(throwables.toString());
            ServiceLogger.LOGGER.severe("Get connection error");
        }


        try {
            PreparedStatement query = con.prepareStatement(
                    "INSERT INTO responses(transaction_id, email, session_id, response, http_status) \n" +
                            "VALUE (?,?,?,?,?);"
            );

            query.setString(1, request.getTransaction_id());
            query.setString(2, request.getEmail());
            query.setString(3, request.getSession_id());
            query.setString(4, response.hasEntity() ? response.readEntity(String.class) : "");
            query.setInt(5, response.getStatus());
            ServiceLogger.LOGGER.warning(query.toString());
            query.executeUpdate();

            //release connection
            GatewayService.getConnectionPoolManager().releaseCon(con);
        }catch (Exception e){
            GatewayService.getConnectionPoolManager().releaseCon(con);
            ServiceLogger.LOGGER.severe(e.toString());
            ServiceLogger.LOGGER.severe("SQL Query failed");
        }

    }

    @Override
    public void run() {
        ServiceLogger.LOGGER.info("Thread start, id:" + id);
        while (true) {
            try {
                ClientRequest request = threadPool.takeRequest();
                ServiceLogger.LOGGER.info("Worker take the request transactionID:" + request.getTransaction_id());
                if (request != null) {
                    process(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//    public static <T,C extends Configs> Response sendRequest(T Request, String endpoint, HttpHeaders headers, C config) {
//        try {
//
//            URI uri;
//
//            uri = UriBuilder.fromUri(config.getScheme() + config.getHostName() + config.getPath()).port(config.getPort()).build();
//            ServiceLogger.LOGGER.info("Build uri: " + uri.toString());
//
//
//            // Create a new Client
//            ServiceLogger.LOGGER.info("Building client...");
//            Client client = ClientBuilder.newClient();
//            client.register(JacksonFeature.class);
//
//            ServiceLogger.LOGGER.info("Building WebTarget...");
//            WebTarget webTarget = client.target(uri.toString()).path(endpoint);
//
//            // Create an InvocationBuilder to create the HTTP request
//            ServiceLogger.LOGGER.info("Starting invocation builder...");
//            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
//
//            // Send the request and save it to a Response
//            ServiceLogger.LOGGER.info("Sending request...");
//            ServiceLogger.LOGGER.info(Entity.entity(Request, MediaType.APPLICATION_JSON).toString());
//            Response response = invocationBuilder
//                    .header("email", headers.getHeaderString("email"))
//                    .header("session_id", headers.getHeaderString("session_id"))
//                    .header("transaction_id", headers.getHeaderString("transaction_id"))
//                    .post(Entity.entity(Request, MediaType.APPLICATION_JSON));
//            ServiceLogger.LOGGER.info("Request sent.");
//
//            ServiceLogger.LOGGER.info("Received status " + response.getStatus());
//            return response;
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
