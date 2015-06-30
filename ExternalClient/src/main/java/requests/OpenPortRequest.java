package requests;

import datamodel.Request;
import httpserver.RequestSender;

public class OpenPortRequest implements Runnable{

    public void run() {
        String commandString = "nmap --open web.info.uvt.ro";

        Request request = new Request();
        request.setClientId("13");
        request.setCommand(commandString);
        request.setResponseAddress("http://localhost:8008/jobFinished");
        request.setProcessors(new String[]{"processors.XmlToJsonConverter"});
        request.setAdapter("adapters.EventHubAdapter");
        String requestResponse = RequestSender.sendRequest("http://localhost:8000/job", request);

    }
}
