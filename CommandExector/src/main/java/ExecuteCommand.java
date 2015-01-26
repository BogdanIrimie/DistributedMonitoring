/**
 * Created by constantin on 26.01.2015.
 */
public class ExecuteCommand {
    public static void main(String[] args) {
        /*
        Command cmd = new Command("ls -l");
        cmd.execute();
        */
        Receiver rec = new Receiver();
        rec.startReceiving();
    }
}
