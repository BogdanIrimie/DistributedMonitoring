import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {

    /**
     * Command to be executed
     */
    private String command;


    public Command() {
        command = null;
    }

    public Command(String cmd) {
        command = cmd;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String cmd) {
        command = cmd;
    }

    public int execute(String cmd) {
        setCommand(cmd);
        return execute();
    }

    /**
     * Start a new process with the provided command
     *
     * @return exit code for process
     */
    public int execute() {
        String line;
        Process p;
        int returnValue = -1;

        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            returnValue = p.exitValue();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return returnValue;
    }
}
