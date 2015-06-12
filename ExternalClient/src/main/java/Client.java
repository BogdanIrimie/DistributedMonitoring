import httpserver.WebServer;
import requests.CiphersuiteRequest;
import requests.Ecrypt2LevelRequest;
import requests.OpenPortRequest;

import java.util.Random;

public class Client {
    public static void main(String[] args) {
        WebServer ws = new WebServer();

        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            switch (r.nextInt(3)) {
                case 0: new Thread(new CiphersuiteRequest()).start();
                        break;
                case 1: new Thread(new Ecrypt2LevelRequest()).start();
                        break;
                case 2: new Thread(new OpenPortRequest()).start();
                        break;
            }
        }
    }
}
