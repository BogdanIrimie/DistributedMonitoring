package datamodel;

/**
 * Request that wil be sent to the PaaS monitoring systems.
 */
public class Request {

    private String clientId;
    private String command;
    private String responseAddress;
    private String[] processors;
    private String adapter;

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

    public String[] getProcessors() {
        return processors;
    }

    public void setProcessors(String[] processors) {
        this.processors = processors;
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

}
