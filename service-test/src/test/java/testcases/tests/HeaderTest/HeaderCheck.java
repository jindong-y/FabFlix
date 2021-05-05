package testcases.tests.HeaderTest;

import javax.ws.rs.core.MultivaluedHashMap;

public class HeaderCheck {

    public static boolean checkHeader(MultivaluedHashMap<String, Object> sentHeader, MultivaluedHashMap<String, Object> receiveHeader){
        return sentHeader.get("email").equals(receiveHeader.get("email")) &&
                sentHeader.get("session_id").equals(receiveHeader.get("session_id")) &&
                sentHeader.get("transaction_id").equals(receiveHeader.get("transaction_id"));
    }
}
