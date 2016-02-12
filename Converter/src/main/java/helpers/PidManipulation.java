package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Operations regarding PID.
 */
public class PidManipulation {
    private static Logger logger = LoggerFactory.getLogger(PidManipulation.class);

    /**
     * Write program PID to file.
     * @param path where to write the pid.
     */
    public static void writeOwnPidToFile(String path) {
        try {
            File file = new File(path);
            file.getParentFile().mkdir();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            FileWriter fw = new FileWriter(file.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(getPid());
            bw.close();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Obtain PID.
     *
     * @return PID of program.
     */
    public static String getPid() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return processName.split("@")[0];
    }
}
