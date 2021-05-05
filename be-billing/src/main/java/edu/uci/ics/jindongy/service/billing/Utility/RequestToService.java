package edu.uci.ics.jindongy.service.billing.Utility;

import edu.uci.ics.jindongy.service.billing.BillingService;
import edu.uci.ics.jindongy.service.billing.configs.Configs;
import edu.uci.ics.jindongy.service.billing.configs.IdmConfigs;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.net.URI;

public class RequestToService {
    public static <T,C extends Configs> Response sendRequest(T Request, String endpoint, HttpHeaders headers, C config) {
        try {





            URI uri;

            uri = UriBuilder.fromUri(config.getScheme() + config.getHostName() + config.getPath()).port(config.getPort()).build();
            ServiceLogger.LOGGER.info("Build uri: " + uri.toString());


            // Create a new Client
            ServiceLogger.LOGGER.info("Building client...");
            Client client = ClientBuilder.newClient();
            client.register(JacksonFeature.class);

            ServiceLogger.LOGGER.info("Building WebTarget...");
            WebTarget webTarget = client.target(uri.toString()).path(endpoint);

            // Create an InvocationBuilder to create the HTTP request
            ServiceLogger.LOGGER.info("Starting invocation builder...");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

            // Send the request and save it to a Response
            ServiceLogger.LOGGER.info("Sending request...");
            ServiceLogger.LOGGER.info(Entity.entity(Request, MediaType.APPLICATION_JSON).toString());
            Response response = invocationBuilder
                    .header("email", headers.getHeaderString("email"))
                    .header("session_id", headers.getHeaderString("session_id"))
                    .header("transaction_id", headers.getHeaderString("transaction_id"))
                    .post(Entity.entity(Request, MediaType.APPLICATION_JSON));
            ServiceLogger.LOGGER.info("Request sent.");

            ServiceLogger.LOGGER.info("Received status " + response.getStatus());
            return response;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
