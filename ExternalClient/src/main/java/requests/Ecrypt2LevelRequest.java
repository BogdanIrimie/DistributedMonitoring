package requests;

import datamodel.Request;
import httpserver.RequestSenderWithMessage;

public class Ecrypt2LevelRequest implements Runnable {

    private String responseAddress;
    private String sendRequestAddress;
    private String scanTargetAddress;

    public Ecrypt2LevelRequest(String responseAddress, String sendRequestAddress, String scanTargetAddress) {
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
        request.setProcessors(new String[]{"processors.XmlToJsonConverter", "processors.TlsCiphersuitesFilter","processors.TlsEcrypt2Level"});
        request.setAdapter("adapters.EventHubAdapter");
        String requestResponse = RequestSenderWithMessage.sendRequest("http://" + sendRequestAddress + ":8000/job", request);
    }
}
