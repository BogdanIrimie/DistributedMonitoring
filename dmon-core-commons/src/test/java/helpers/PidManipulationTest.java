package helpers;

import dmon.core.commons.helpers.PidManipulation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class PidManipulationTest {
    private static final Logger logger = LoggerFactory.getLogger(PidManipulationTest.class);

    /**
     * Write PID to file.
     */
    @Test
    public void writePidTest() {
        String pidFile = "etc/pidFile.pid";
        PidManipulation.writeOwnPidToFile(pidFile);

        try {
            Scanner scanner = new Scanner(new FileReader(pidFile));
            int pid = scanner.nextInt();
            assertTrue(PidManipulation.getPid().equals(Integer.toString(pid)));
            PidManipulation.removePidFile();
        } catch (FileNotFoundException e) {
            assert false;
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Throw exception of file creation.
     */
    @Test
    public void exceptionOnWritePidFile() {
        PidManipulation.writeOwnPidToFile("/etc/pidFile.pid");
    }
}
