package requests;

public class RequestExecutor {

    public static void generateRequests() {

        final String responseAddress = "104.155.61.52";
        final String sendRequestAddress = "104.155.61.159";
        final String scanTargetAddress = "google.com";
        final String scanTargetAddress2 = "";



        for (int i = 0; i < 10; i++ ) {
            new Thread(new CiphersuiteRequest(responseAddress, sendRequestAddress, scanTargetAddress)).start();
        }
        /*
        for (int i = 0; i < 3; i++) {
            new Thread(new Ecrypt2LevelRequest(responseAddress, sendRequestAddress, scanTargetAddress)).start();
        }
        */
/*
        for(int i = 0; i < 10; i++ ) {
            new Thread(new OpenPortRequest(responseAddress, sendRequestAddress, scanTargetAddress)).start();
        }
*/

    }
}
