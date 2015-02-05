package datamodel;

/**
 * Commands that can be executed by the CommandExecutor
 */
public class Command {
    /**
     * Command that can be executed
     */
    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public Command(String command) {
        this.command = command;
    }

}
