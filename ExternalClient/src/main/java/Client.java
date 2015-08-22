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
        //WebServer ws = new WebServer();

        /*
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
        */
        RequestExecutor.generateRequests();
    }
}
