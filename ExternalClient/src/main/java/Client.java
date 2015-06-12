import httpserver.WebServer;
import requests.CiphersuiteRequest;

public class Client {
    public static void main(String[] args) {
        WebServer ws = new WebServer();

        for (int i = 0; i < 10; i++) {
            new Thread(new CiphersuiteRequest()).start();
        }
    }
}
