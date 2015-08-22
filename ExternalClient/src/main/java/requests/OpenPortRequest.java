package requests;

import datamodel.Request;
import httpserver.RequestSender;

public class OpenPortRequest implements Runnable{

    private String responseAddress;
    private String sendRequestAddress;
    private String scanTargetAddress;

    public OpenPortRequest(String responseAddress, String sendRequestAddress, String scanTargetAddress) {
        this.responseAddress = responseAddress;
        this.sendRequestAddress = sendRequestAddress;
        this.scanTargetAddress = scanTargetAddress;
    }

    public void run() {
        String commandString = "nmap --host-timeout 8s --open " + scanTargetAddress;

        Request request = new Request();
        request.setClientId("13");
        request.setCommand(commandString);
        request.setResponseAddress("http://" + responseAddress + ":8008/jobFinished");
        request.setProcessors(new String[]{"processors.XmlToJsonConverter"});
        request.setAdapter("adapters.EventHubAdapter");
        RequestSender rs = new RequestSender();
        String requestResponse = rs.sendRequest("http://" + sendRequestAddress + ":8000/job", request);

    }
}
