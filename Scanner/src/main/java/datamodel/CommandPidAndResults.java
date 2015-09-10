package datamodel;


public class CommandPidAndResults {
    private long commadnPid;
    private String commandResults;

    public CommandPidAndResults(long commadnPid, String commandResults) {
        this.commadnPid = commadnPid;
        this.commandResults = commandResults;
    }

    public long getCommadnPid() {
        return commadnPid;
    }

    public void setCommadnPid(long commadnPid) {
        this.commadnPid = commadnPid;
    }

    public String getCommandResults() {
        return commandResults;
    }

    public void setCommandResults(String commandResults) {
        this.commandResults = commandResults;
    }
}
