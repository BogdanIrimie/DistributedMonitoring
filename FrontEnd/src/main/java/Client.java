import rabbit.Sender;

public class Client {
    public static void main(String[] args) {
        Sender sender = new Sender();
        for (int i = 0; i < 1; i++) {
            sender.Send(i + "", "nmap -T4 -A -v info.uvt.ro");
        }
        sender.closeConnection();
    }
}
