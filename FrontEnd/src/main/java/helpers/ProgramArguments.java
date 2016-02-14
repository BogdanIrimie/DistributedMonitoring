package helpers;

/**
 * Program arguments that can be inserted by the user.
 */
public class ProgramArguments {
    private static String pidFile = null;
    private static String configFile = null;
    private static String logPath = null;

    public static String getPidFile() {
        return pidFile;
    }

    public static void setPidFile(String pidFile) {
        ProgramArguments.pidFile = pidFile;
    }

    public static String getConfigFile() {
        return configFile;
    }

    public static void setConfigFile(String configFile) {
        ProgramArguments.configFile = configFile;
    }

    public static String getLogPath() {
        return logPath;
    }

    public static void setLogPath(String logPath) {
        ProgramArguments.logPath = logPath;
    }
}
