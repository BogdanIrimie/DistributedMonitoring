package datamodel;

/**
 * Data model used to store command and PID
 */
public class CommandPidAndResults {
    private long commandPid;
    private String commandResults;

    public CommandPidAndResults(long commandPid, String commandResults) {
        this.commandPid = commandPid;
        this.commandResults = commandResults;
    }

    public long getCommandPid() {
        return commandPid;
    }

    public void setCommandPid(long commandPid) {
        this.commandPid = commandPid;
    }

    public String getCommandResults() {
        return commandResults;
    }

    public void setCommandResults(String commandResults) {
        this.commandResults = commandResults;
    }
}
