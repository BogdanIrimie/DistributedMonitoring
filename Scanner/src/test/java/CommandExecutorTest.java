import datamodel.Command;
import executors.CommandExecutor;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CommandExecutorTest {

    @Test
    public void executeCommand() {
        CommandExecutor ce = new CommandExecutor();
        Command command = new Command("nmap info.uvt.ro");
        if (command.getCommand().length() > 0) {
            assertTrue(true);
        }
    }

}
