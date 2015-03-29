package datamodel;

/**
 * Request that wil be sent to the PaaS monitoring systems.
 */
public class Request {

    private String clientId;
    private String command;
    private String responseAddress;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponseAddress() {
        return responseAddress;
    }

    public void setResponseAddress(String responseAddress) {
        this.responseAddress = responseAddress;
    }

}
