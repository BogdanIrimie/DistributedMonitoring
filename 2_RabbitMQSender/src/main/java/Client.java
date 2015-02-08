import rabbit.Sender;

public class Client {
    public static void main(String[] args) {
        Sender sender = new Sender();
        sender.Send("13", "nmap -T4 -A -v info.uvt.ro");
        sender.closeConnection();
    }
}
