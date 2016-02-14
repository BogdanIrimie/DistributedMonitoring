package helpers;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse command line arguments.
 */
public class CommandLineArgumentParser {
    private static Logger logger = LoggerFactory.getLogger(CommandLineArgumentParser.class);
    private String[] args;
    private Options options = new Options();

    /**
     * Initialize options supported by the program.
     *
     * @param args program arguments.
     */
    public CommandLineArgumentParser(String[] args) {
        this.args = args;
        options.addOption("h", "help", false, "Show help.");
        options.addOption("p", "pidFile", true, "Set file to store PID.");
        options.addOption("c", "configFile", true, "Set config file.");
        options.addOption("l", "logPath", true, "Set path to log files.");
    }

    /**
     * Parse program arguments.
     */
    public void parse() {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                help();
            }

            String pidFile = null;
            if (cmd.hasOption("p")) {
                pidFile = cmd.getOptionValue("p");
                ProgramArguments.setPidFile(pidFile);
            }
            else {
                ProgramArguments.setPidFile("../var/specs_monitoring_nmap_frontend.pid");
            }

            String configFile = null;
            if (cmd.hasOption("c")) {
                configFile =  cmd.getOptionValue("c");
                ProgramArguments.setConfigFile(configFile);
            }
            else {
                ProgramArguments.setConfigFile("../etc/conf.properties");
            }

            String logPath = null;
            if (cmd.hasOption("l")) {
                logPath = cmd.getOptionValue("l");
                ProgramArguments.setLogPath(logPath);
            }
            else {
                ProgramArguments.setLogPath("../var/");
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Print help messages.
     */
    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar frontend.jar [arguments] [file]", options);
        System.exit(0);
    }
}
