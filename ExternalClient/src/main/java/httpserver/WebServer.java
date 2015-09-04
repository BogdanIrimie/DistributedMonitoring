package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javafx.scene.control.TextArea;
import requests.RequestExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class WebServer {

    private static final int TOTAL_SCANS = 10;
    private static int TOTAL_RUNS = 5;
    private static int run = 1;
    private static TextArea results = null;
    private static int resultsCounter = 0;
    private static long startTime, endTime;
    private List<Long> runTimes = new ArrayList<Long>();
    private HttpServer server = null;;

    public WebServer() {
        this(8008);
    }

    public WebServer(int port, int totalRuns) {
        this(port);
        TOTAL_RUNS = totalRuns;
    }

    public WebServer(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/jobFinished", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started listening on port 8008.");
        startTime = System.currentTimeMillis();
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

            //System.out.println(receivedData.toString());

            //System.out.println("Received data: " + receivedData.toString());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            if (resultsCounter == 0) {
                System.out.print("Run " + run + ": ");
            }
            resultsCounter++;
            System.out.print(resultsCounter + " ");
            if (resultsCounter == TOTAL_SCANS) {
                endTime = System.currentTimeMillis();
                long elapsedTime = (endTime - startTime)/1000;
                System.out.println(" -- time elapsed: " +  elapsedTime + " seconds");
                runTimes.add(elapsedTime);
                run++;
                resultsCounter = 0;
                if (run <= TOTAL_RUNS) {
                    startTime = System.currentTimeMillis();
                    RequestExecutor.generateRequests();
                }
                else {
                    int i = 0;
                    System.out.println("run, time");
                    for (Long time : runTimes) {
                        i++;
                        System.out.println(i + ", " + time);
                    }
                    if (server != null) {
                        server.stop(1);
                    }
                }
            }
        }
    }

}