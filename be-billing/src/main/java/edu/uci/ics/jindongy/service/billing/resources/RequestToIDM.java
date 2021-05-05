package edu.uci.ics.jindongy.service.billing.resources;

import edu.uci.ics.jindongy.service.billing.BillingService;
import edu.uci.ics.jindongy.service.billing.Utility.RequestToService;
import edu.uci.ics.jindongy.service.billing.configs.IdmConfigs;
import edu.uci.ics.jindongy.service.billing.logger.ServiceLogger;
import edu.uci.ics.jindongy.service.billing.models.Request.IDMRequest;
import edu.uci.ics.jindongy.service.billing.models.Response.IDMResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class RequestToIDM{



    public boolean checkPrivilege(HttpHeaders headers, boolean hidden) throws BadRequestException {
        int rc =requestPrivilege(headers);
        if (rc == 140) {
            return hidden;
        }
        if (rc == 141) {
            return false;
        }
        throw new BadRequestException("Privilege request error\n");

    }


    public static int requestPrivilege(HttpHeaders headers) {
        String email = headers.getHeaderString("email");

        Response response = RequestToService.sendRequest(new IDMRequest(email, 4), BillingService.getIdmConfigs().getPrivilegePath(),headers, BillingService.getIdmConfigs());

        ServiceLogger.LOGGER.info("Reading response");
        IDMResponse payload = null;

        try {
            payload = response.readEntity(IDMResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            ServiceLogger.LOGGER.severe("reading response error");

        }

        int rc = payload.getResultCode();
        ServiceLogger.LOGGER.info("Received resultCode: " + payload.getResultCode());
        ServiceLogger.LOGGER.info("Received Message: " + payload.getMessage());
        if(rc<0)throw new BadRequestException("Privilege request error\n");

        return rc;

    }

}
