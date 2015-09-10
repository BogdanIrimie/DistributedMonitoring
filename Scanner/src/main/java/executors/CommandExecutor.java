package executors;

import datamodel.Command;
import datamodel.CommandPidAndResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

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
    public CommandPidAndResults execute(Command command) {
        String line;
        Process process;
        long commandPid = -1;
        StringBuilder commandOutput = new StringBuilder();

        processCommand(command);

        try {
            process = Runtime.getRuntime().exec(command.getCommand());

            Field pid = process.getClass().getDeclaredField("pid");
            pid.setAccessible(true);
            commandPid = pid.getLong(process);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                commandOutput.append(line);
            }
            process.waitFor();
            process.exitValue();  // throw some error if exit value is incorrect
            process.destroy();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        logger.info("Command output: " + commandOutput.toString());
        return new CommandPidAndResults(commandPid, commandOutput.toString());
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
