package datamodel;

/**
 * Model for data in DB
 */
public class Measurement {
    private MongoId _id;
    private String clientId;
    private String command;
    private String responseAddress;
    private String[] processors;
    private String adapter;
    private String rawResult;
    private String processedResult;

    public Measurement() {}

    public Measurement(String clientId, String command, String responseAddress) {
        this.clientId = clientId;
        this.command = command;
        this.responseAddress = responseAddress;
    }

    public Measurement(String clientId, String command, String responseAddress, String[] processors, String adapter) {
        this.clientId = clientId;
        this.command = command;
        this.responseAddress = responseAddress;
        this.processors = processors;
        this.adapter = adapter;
    }

    public MongoId get_id() {
        return _id;
    }

    public void set_id(MongoId _id) {
        this._id = _id;
    }

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

    public String getRawResult() {
        return rawResult;
    }

    public void setRawResult(String rawResult) {
        this.rawResult = rawResult;
    }

    public String getProcessedResult() {
        return processedResult;
    }

    public void setProcessedResult(String processedResult) {
        this.processedResult = processedResult;
    }

}
