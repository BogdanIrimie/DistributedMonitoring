import httpserver.RequestSender;
import httpserver.WebServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    private WebServer ws = new WebServer();
    public TextField command;
    @FXML
    public TextArea results;

    public void runCommand(ActionEvent actionEvent) {
        results.setText("");
        ws.setResults(results);
        Thread th = new Thread() {
            @Override
            public void run() {
                String commandString = command.getText();
                RequestSender.sendRequest("http://localhost:8000/job", "13", commandString, "http://localhost:8008/jobFinished");
            }
        };
        th.start();
    }

}
