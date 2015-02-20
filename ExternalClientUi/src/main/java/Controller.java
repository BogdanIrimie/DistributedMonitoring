import httpserver.RequestSender;
import httpserver.WebServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    public TextField command;
    @FXML
    public TextArea results;

    public void runCommand(ActionEvent actionEvent) {
        WebServer ws = new WebServer();
        ws.setResults(results);
        String commandString = command.getText();
        RequestSender.sendRequest("http://localhost:8000/job", "13", commandString, "http://localhost:8008/jobFinished");
    }

}
