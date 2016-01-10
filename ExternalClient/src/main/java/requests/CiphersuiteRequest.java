package requests;

import datamodel.Request;
import httpserver.RequestSenderWithMessage;

/*
*   Request ciphersuite list
* */
public class CiphersuiteRequest implements Runnable {

    private String responseAddress;
    private String sendRequestAddress;
    private String scanTargetAddress;

    public CiphersuiteRequest(String responseAddress, String sendRequestAddress, String scanTargetAddress) {
        this.responseAddress = responseAddress;
        this.sendRequestAddress = sendRequestAddress;
        this.scanTargetAddress = scanTargetAddress;
    }

    public void run() {
        String commandString = "security tls " + scanTargetAddress;

        Request request = new Request();
        request.setClientId("13");
        request.setCommand(commandString);
        request.setResponseAddress("http://" + responseAddress + ":8008/jobFinished");
        request.setProcessors(new String[]{"XmlToJsonConverter", "security.TlsCiphersuitesFilter"});
        request.setAdapter("adapters.EventHubAdapter");
        RequestSenderWithMessage requestSender = new RequestSenderWithMessage();
        String requestResponse = requestSender.sendRequest("http://" + sendRequestAddress + ":8080/request", request);
    }
}
