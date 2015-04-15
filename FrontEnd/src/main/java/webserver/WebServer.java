package webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import converters.JsonConverter;
import datamodel.Request;
import datamodel.RequestResponse;
import datamodel.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabbit.Sender;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private final String CHARSET = StandardCharsets.UTF_8.name();

    public WebServer() {
        this(8000);
    }

    public WebServer(int port) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/job", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            logger.info("Server started and listening on port 8000. Example of request: " +
                    "/job?id=13&command=nmap%20info.uvt.ro&responseAddress=http://localhost:8008/jobFinished");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Handle HTTP requests.
     */
    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response;

            String paramQuery = t.getRequestURI().getQuery();
            Map<String, String> params = queryToMap(paramQuery);
            String clientIp = t.getRemoteAddress().getAddress().getHostAddress();


            String requestJsonString = URLDecoder.decode(params.get("request"), CHARSET);
            Request request = JsonConverter.jsonStringToObject(requestJsonString, Request.class);

            RequestResponse requestResponse = new RequestResponse();
            if ((request.getClientId() != null) && (request.getCommand() != null)) {
                Sender sender = new Sender();
                String jobId = sender.send(request, clientIp);
                sender.closeConnection();

                requestResponse.setStatus(Status.valid);
                requestResponse.setJobId(jobId);

                response = JsonConverter.objectToJsonString(requestResponse);
                t.sendResponseHeaders(202, response.length());
            }
            else {
                requestResponse.setStatus(Status.invalid);
                requestResponse.setJobId(null);

                response = JsonConverter.objectToJsonString(requestResponse);
                t.sendResponseHeaders(400, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * Return a map with the parameters of the HTTP request.
     *
     * @param query HTTP query
     * @return map with parameter name and value
     */
    private Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

}
