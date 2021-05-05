package edu.uci.ics.jindongy.service.billing.resources;

import edu.uci.ics.jindongy.service.billing.BillingService;
import edu.uci.ics.jindongy.service.billing.Utility.RequestToService;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.Request.ThumbnailRequest;
import edu.uci.ics.jindongy.service.billing.models.Response.ThumbnailResponse;
import edu.uci.ics.jindongy.service.billing.models.data.ThumbnailModel;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class RequestToMovie {

    public static ThumbnailModel[] retrieveThumbnail(String[] movie_ids, HttpHeaders headers){
        ServiceLogger.LOGGER.info("Send request to Movie Thumbnail");

        Response response = RequestToService.sendRequest(new ThumbnailRequest(movie_ids), BillingService.getMoviesConfigs().getThumbnailPath(),headers,BillingService.getMoviesConfigs());

        ServiceLogger.LOGGER.info("Reading response from Thumbnail");
        ThumbnailResponse payload = null;

        try {
            payload = response.readEntity(ThumbnailResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            ServiceLogger.LOGGER.severe("reading response error");
        }
        return payload.getThumbnails();


    }

}
