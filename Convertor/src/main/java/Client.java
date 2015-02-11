import rabbit.Receiver;

public class Client {
    public static void main(String[] args) {
        Receiver rec = new Receiver();
        rec.startReceiving();
    }

}
