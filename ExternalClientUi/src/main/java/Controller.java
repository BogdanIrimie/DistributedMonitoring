import datamodel.Request;
import httpserver.RequestSenderWithMessage;
import httpserver.WebServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    public TextField response;
    private WebServer ws = new WebServer();
    public TextField command;
    @FXML
    public TextArea results;

    @FXML
    public void initialize(){
        ws.setResults(results);
    }


    public void runCommand(ActionEvent actionEvent) {
        results.setText("");
        response.setText("");
        Thread th = new Thread() {
            @Override
            public void run() {
                String commandString = command.getText();

                Request request = new Request();
                request.setClientId("13");
                request.setCommand(commandString);
                request.setResponseAddress("http://127.0.0.1:8008/jobFinished");

                String[] tocCommandString = commandString.split("\\s+");
                switch (tocCommandString[0]) {
                    case "availability": request.setProcessors(new String[] {"availability.HttpStatusCodeFilter"});
                                         break;
                    case "security":
                        switch (tocCommandString[1]) {
                            case "tls": request.setProcessors(new String[] {
                                    "XmlToJsonConverter",
                                    "security.TlsCiphersuitesFilter"});
                                    break;
                            case "ecrypt2lvl": request.setProcessors(new String[] {
                                    "XmlToJsonConverter",
                                    "security.TlsCiphersuitesFilter",
                                    "security.TlsEcrypt2Level"});
                                    break;
                            case "open_ports": request.setProcessors(new String[]{"XmlToJsonConverter"});
                                              break;
                        }
                        break;
                    default: request.setProcessors(new String[]{});
                }

                request.setAdapter("adapters.EventHubAdapter");

                String requestResponse = RequestSenderWithMessage.sendRequest("http://127.0.0.1:8080/request", request);
                Platform.runLater(new Runnable() {
                    public void run() {
                        response.setText(requestResponse);
                    }
                });
            }
        };
        th.start();
    }

}
