package executors;

import datamodel.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes commands in terminal
 */
public class CommandExecutor {

    /**
     * Start a new process with the provided command
     *
     * @return exit code for process
     */
    public String execute(Command command) {
        String line;
        Process p;
        StringBuilder commandOutput = new StringBuilder();

        try {
            p = Runtime.getRuntime().exec(command.getCommand());
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                commandOutput.append(line);
                System.out.println(line);
            }
            p.waitFor();
            p.exitValue();  // throw some error if exit value is incorrect
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return commandOutput.toString();
    }
}
