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
                request.setResponseAddress("http://localhost:8008/jobFinished");
                //request.setProcessors(new String[] {"processors.XmlToJsonConverter","processors.TlsCiphersuitesFilter","processors.TlsEcrypt2Level"});
                request.setProcessors(new String[] {"processors.HttpStatusCodeAndBodyFilter"});
                request.setAdapter("adapters.EventHubAdapter");

                String requestResponse = RequestSenderWithMessage.sendRequest("http://localhost:8080/request", request);
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
