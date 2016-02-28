package dmon.core.commons.helpers;

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
    private static final Logger logger = LoggerFactory.getLogger(PidManipulation.class);
    private static File file;

    /**
     * Write program PID to file.
     * @param path where to write the pid.
     */
    public static void writeOwnPidToFile(String path) {
        try {
            file = new File(path);
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdir();
                }

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

    public static void removePidFile() {
        file.delete();
        file.getParentFile().delete();
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
