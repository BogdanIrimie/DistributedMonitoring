package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {

    private static TextArea results = null;

    public WebServer() {
        this(8008);
    }

    public WebServer(int port) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/jobFinished", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started listening on port 8008.");
    }

    public static TextArea getResults() {
        return results;
    }

    public static void setResults(TextArea results) {
        WebServer.results = results;
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String line;
            String response = "Results received";
            BufferedReader br = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            final StringBuilder receivedData = new StringBuilder();

            while ((line = br.readLine()) != null) {
                receivedData.append(line);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // update UI:
                    try {
                        JSONObject json = new JSONObject(receivedData.toString());
                        results.setText(json.toString(4));
                    }
                    catch (Exception e) {
                        results.setText(receivedData.toString());
                    }
                }
            });


            System.out.println("Received data: " + receivedData.toString());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
