package executors;

import datamodel.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {

    /**
     * Start a new process with the provided command
     *
     * @return exit code for process
     */
    public int execute(Command command) {
        String line;
        Process p;
        int returnValue = -1;

        try {
            p = Runtime.getRuntime().exec(command.getCommand());
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
