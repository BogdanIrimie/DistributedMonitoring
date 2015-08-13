package executors;

import datamodel.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes commands in terminal
 */
public class CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    /**
     * Start a new process with the provided command
     *
     * @return command output
     */
    public String execute(Command command) {
        String line;
        Process p;
        StringBuilder commandOutput = new StringBuilder();

        processCommand(command);

        try {
            p = Runtime.getRuntime().exec(command.getCommand());
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                commandOutput.append(line);
            }
            p.waitFor();
            p.exitValue();  // throw some error if exit value is incorrect
            p.destroy();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Command output: " + commandOutput.toString());
        return commandOutput.toString();
    }

    /**
     * Process the command and add or remove arguments.
     * @param command that will be processed.
     */
    private void processCommand(Command command) {
        ResultFormat format = ResultFormat.STANDARD;

        // if the command is a nmap command than add the argument to get results as XML
        if (command.getCommand().matches("^nmap.*")) {
            format = ResultFormat.XML;
        }

        switch (format) {
            case STANDARD:
                break;
            case XML:
                if (!command.getCommand().contains("-oX -")) {
                    String newCommand = command.getCommand() + " -oX -";
                    command.setCommand(newCommand);
                }
                break;
        }
    }

}
