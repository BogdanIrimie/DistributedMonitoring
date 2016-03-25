package dmon.core.commons.datamodel;

import java.util.Arrays;

/**
 * Model for data in DB
 */
public class Measurement {
    private MongoId _id;
    private String clientId;
    private String clientIp;
    private String userCommand;
    private String responseAddress;
    private int repetitions;
    private int repetitionInterval;
    private String[] processors;
    private String adapter;
    private String rawResult;
    private String processedResult;

    public Measurement() {}

    public Measurement(String clientId, String clientIp, String userCommand, String responseAddress,
                       int repetitions, int repetitionInterval, String[] processors, String adapter) {
        this.clientId = clientId;
        this.clientIp = clientIp;
        this.userCommand = userCommand;
        this.responseAddress = responseAddress;
        this.repetitions = repetitions;
        this.repetitionInterval = repetitionInterval;
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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserCommand() {
        return userCommand;
    }

    public void setUserCommand(String userCommand) {
        this.userCommand = userCommand;
    }

    public String getResponseAddress() {
        return responseAddress;
    }

    public void setResponseAddress(String responseAddress) {
        this.responseAddress = responseAddress;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRepetitionInterval() {
        return repetitionInterval;
    }

    public void setRepetitionInterval(int repetitionInterval) {
        this.repetitionInterval = repetitionInterval;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientIp != null ? !clientIp.equals(that.clientIp) : that.clientIp != null) return false;
        if (userCommand != null ? !userCommand.equals(that.userCommand) : that.userCommand != null) return false;
        if (responseAddress != null ? !responseAddress.equals(that.responseAddress) : that.responseAddress != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(processors, that.processors)) return false;
        if (adapter != null ? !adapter.equals(that.adapter) : that.adapter != null) return false;
        if (rawResult != null ? !rawResult.equals(that.rawResult) : that.rawResult != null) return false;
        return processedResult != null ? processedResult.equals(that.processedResult) : that.processedResult == null;
    }
}
