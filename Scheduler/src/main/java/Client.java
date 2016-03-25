import rabbit.Receiver;

public class Client {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        receiver.startReceiving();
    }
}
