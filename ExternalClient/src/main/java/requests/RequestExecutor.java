package requests;


public class RequestExecutor {

    public static void generateRequests() {

        final String responseAddress = "104.155.8.70";
        final String sendRequestAddress = "146.148.126.140";
        final String scanTargetAddress = "104.155.44.242";
        final String scanTargetAddress2 = "";

        for (int i = 0; i < 334; i++ ) {
            new Thread(new CiphersuiteRequest(responseAddress, sendRequestAddress, scanTargetAddress)).start();
        }

        for (int i = 0; i < 333; i++) {
            new Thread(new Ecrypt2LevelRequest(responseAddress, sendRequestAddress, scanTargetAddress)).start();
        }

        for(int i = 0; i < 333; i++ ) {
            new Thread(new OpenPortRequest(responseAddress, sendRequestAddress, scanTargetAddress)).start();
        }
    }

}
