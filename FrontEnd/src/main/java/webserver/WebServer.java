package webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import rabbit.Sender;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebServer {

    private final String CHARSET = StandardCharsets.UTF_8.name();

    public WebServer() {
        this(8000);
    }

    public WebServer(int port) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/job", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started and listening on port 8000. Example of request: /job?id=13&command=nmap%20info.uvt.ro&responseaddress=http://localhost:8008/jobFinished");
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response;

            String paramQuery = t.getRequestURI().getQuery();
            Map<String, String> params = queryToMap(paramQuery);

            String id = URLDecoder.decode(params.get("id"), CHARSET);
            String command = URLDecoder.decode(params.get("command"), CHARSET);
            String responseAddress = URLDecoder.decode(params.get("responseaddress"), CHARSET);

            if ((id != null) && (command != null)) {
                response = "Request valid, processing will start soon.";
                t.sendResponseHeaders(202, response.length());
                Sender sender = new Sender();
                sender.send(id, command, responseAddress);
                sender.closeConnection();
            }
            else {
                response = "Request is invalid, id or command not present. Valid format could be: id=3&command=nmap%20info.uvt.ro";
                t.sendResponseHeaders(400, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

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
