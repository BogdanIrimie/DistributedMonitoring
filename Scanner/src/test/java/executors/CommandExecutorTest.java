package executors;

import datamodel.Command;
import datamodel.CommandPidAndResults;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class CommandExecutorTest {

    /**
     * Test if commands can be executed
     */
    @Test
    public void executeCommandsTest() {
        CommandExecutor ce = new CommandExecutor();
        Command command = new Command("echo This is it");
        CommandPidAndResults result = ce.execute(command);
        assertTrue(result.getCommandResults().equals("This is it"));
    }

}
