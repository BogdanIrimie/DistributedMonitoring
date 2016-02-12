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
        options.addOption("h", "help", false, "Show help");
        options.addOption("p", "pidFile", true, "Set file to store PID.");
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
                if (pidFile.length() > 0) {
                    PidManipulation.writeOwnPidToFile(pidFile);
                }
            }
            else {
                PidManipulation.writeOwnPidToFile("../var/specs_monitoring_nmap_presenter.pid");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print help messages.
     */
    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar presenter.jar [arguments] [file]", options);
        System.exit(0);
    }
}
