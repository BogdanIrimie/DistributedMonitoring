package requests;

import datamodel.Request;
import httpserver.RequestSender;

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
        String commandString = "nmap --host-timeout 8s --script ssl-enum-ciphers -p 443 " + scanTargetAddress;

        Request request = new Request();
        request.setClientId("13");
        request.setCommand(commandString);
        request.setResponseAddress("http://" + responseAddress + ":8008/jobFinished");
        request.setProcessors(new String[]{"processors.XmlToJsonConverter", "processors.TlsCiphersuitesFilter"});
        request.setAdapter("adapters.EventHubAdapter");
        RequestSender rs = new RequestSender();
        String requestResponse = rs.sendRequest("http://" + sendRequestAddress + ":8000/job", request);
    }
}
