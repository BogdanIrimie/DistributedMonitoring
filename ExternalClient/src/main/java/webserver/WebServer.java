package webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class WebServer {

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
        System.out.println("Server started and listening on port 8008.");
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String line;
            String response = "Results received";
            BufferedReader br = new BufferedReader(new InputStreamReader(t.getRequestBody()));
            StringBuilder receivedData = new StringBuilder();

            while ((line = br.readLine()) != null) {
                receivedData.append(line);
            }

            System.out.println("Received data: " + receivedData.toString());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
