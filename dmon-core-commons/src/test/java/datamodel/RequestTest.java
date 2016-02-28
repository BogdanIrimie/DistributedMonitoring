package datamodel;

import dmon.core.commons.datamodel.Request;
import org.junit.Test;

public class RequestTest {

    /**
     * Test constructor, getters and setters.
     */
    @Test
    public void createRequestObjects() {
        Request request = new Request();

        request.setClientId("13");
        request.getClientId();

        request.setCommand("security tls 192.168.56.101");
        request.getCommand();

        request.setResponseAddress("192.168.56.100");
        request.getResponseAddress();

        request.setProcessors(
                new String[]{
                        "XmlToJsonConverter",
                        "security.TlsCiphersuitesFilter",
                        "security.TlsEcrypt2Level"}
        );
        request.getProcessors();

        request.setAdapter("adapters.EventHubAdapter");
        request.getAdapter();
    }
}
