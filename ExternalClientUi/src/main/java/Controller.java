import converters.JsonConverter;
import datamodel.Request;
import httpserver.RequestSender;
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

    public void runCommand(ActionEvent actionEvent) {
        results.setText("");
        response.setText("");
        ws.setResults(results);
        Thread th = new Thread() {
            @Override
            public void run() {
                String commandString = command.getText();

                Request request = new Request();
                request.setClientId("13");
                request.setCommand(commandString);
                request.setResponseAddress("http://localhost:8008/jobFinished");

                String requestJsonString = JsonConverter.objectToJsonString(request);

                String requestResponse = RequestSender.sendRequest("http://localhost:8000/job", request);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        response.setText(requestResponse);
                    }
                });
            }
        };
        th.start();
    }

}
