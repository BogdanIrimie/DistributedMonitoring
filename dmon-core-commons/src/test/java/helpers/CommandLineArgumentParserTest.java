package helpers;

import dmon.core.commons.helpers.CommandLineArgumentParser;
import dmon.core.commons.helpers.ProgramArguments;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class CommandLineArgumentParserTest {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineArgumentParserTest.class);

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    /**
     * Provide no arguments. The defaults will be used.
     */
    @Test
    public void noArgumentsTest() {
        CommandLineArgumentParser clap = new CommandLineArgumentParser(null);
        clap.parse();
    }

    /**
     * Test help argument.
     */
    @Test
    public void createCommandLineArgumentParserObjects() {
        CommandLineArgumentParser clap = new CommandLineArgumentParser(new String[] {"-h"});
        exit.expectSystemExit();
        clap.parse();
    }

    /**
     * Test pid file argument.
     */
    @Test
    public void pidArgumentsTest() {
        String pidFile = "pidFile.pid";

        CommandLineArgumentParser clap = new CommandLineArgumentParser(new String[] {"-p", pidFile});
        clap.parse();
        assertTrue(ProgramArguments.getPidFile().equals(pidFile));

        clap = new CommandLineArgumentParser(new String[] {"--pidFile", pidFile});
        clap.parse();
        assertTrue(ProgramArguments.getPidFile().equals(pidFile));
    }

    /**
     * Test config argument.
     */
    @Test
    public void configArgumentTest() {
        String configFile = "config.properties";

        CommandLineArgumentParser clap = new CommandLineArgumentParser(new String[] {"-c", configFile});
        clap.parse();
        assertTrue(ProgramArguments.getConfigFile().equals(configFile));

        clap = new CommandLineArgumentParser(new String[] {"--configFile", configFile});
        clap.parse();
        assertTrue(ProgramArguments.getConfigFile().equals(configFile));
    }

    /**
     * Test log argument.
     */
    @Test
    public void logArgumentTest() {
        String logPath = "../var/";

        CommandLineArgumentParser clap = new CommandLineArgumentParser(new String[] {"-l", logPath});
        clap.parse();
        assertTrue(ProgramArguments.getLogPath().equals(logPath));

        clap = new CommandLineArgumentParser(new String[] {"--logPath", logPath});
        clap.parse();
        assertTrue(ProgramArguments.getLogPath().equals(logPath));
    }

    /**
     * Test exception for missing argument.
     */
    @Test
    public void parseExceptionTest() {
        logger.error("We are expecting an exception!");
        CommandLineArgumentParser clap = new CommandLineArgumentParser(new String[] {"-p"});
        clap.parse();
    }

}
