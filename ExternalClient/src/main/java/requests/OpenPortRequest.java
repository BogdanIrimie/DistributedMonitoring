package requests;

import datamodel.Request;
import httpserver.RequestSenderWithMessage;

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
        String commandString = "security open_ports " + scanTargetAddress;

        Request request = new Request();
        request.setClientId("13");
        request.setCommand(commandString);
        request.setResponseAddress("http://" + responseAddress + ":8008/jobFinished");
        request.setProcessors(new String[]{"XmlToJsonConverter"});
        request.setAdapter("adapters.EventHubAdapter");
        RequestSenderWithMessage requestSender = new RequestSenderWithMessage();
        String requestResponse = requestSender.sendRequest("http://" + sendRequestAddress + ":8080/request", request);
    }
}
