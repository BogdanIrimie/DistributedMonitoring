package requests;

import datamodel.Request;
import httpserver.RequestSenderWithMessage;

public class Ecrypt2LevelRequest implements Runnable {
    public void run() {
        String commandString = "nmap --script ssl-enum-ciphers -p 443 web.info.uvt.ro";

        Request request = new Request();
        request.setClientId("13");
        request.setCommand(commandString);
        request.setResponseAddress("http://localhost:8008/jobFinished");
        request.setProcessors(new String[]{"processors.XmlToJsonConverter", "processors.TlsCiphersuitesFilter","processors.TlsEcrypt2Level"});
        request.setAdapter("adapters.EventHubAdapter");
        String requestResponse = RequestSenderWithMessage.sendRequest("http://localhost:8080/request", request);
    }
}

