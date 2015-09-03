import httpserver.WebServer;
import requests.RequestExecutor;

public class Client {
    public static void main(String[] args) {
        int totalRuns;

        if (args.length > 0) {
            totalRuns = Integer.parseInt(args[0]);
        }
        else {
            totalRuns = 1;
        }

        WebServer ws = new WebServer(8008, totalRuns);
        RequestExecutor.generateRequests();
    }
}
