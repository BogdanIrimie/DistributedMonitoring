package datamodel;

/**
 * Model for data in DB
 */
public class Measurement {
    private MongoId _id;
    private String clientId;
    private String responseAddress;
    private String command;
    private String xmlDocument;
    private String jsonDocument;

    public Measurement() {}

    public Measurement(String clientId, String command, String responseAddress) {
        this.clientId = clientId;
        this.command = command;
        this.responseAddress = responseAddress;
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

    public String getXmlDocument() {
        return xmlDocument;
    }

    public void setXmlDocument(String xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    public String getJsonDocument() {
        return jsonDocument;
    }

    public void setJsonDocument(String jsonDocument) {
        this.jsonDocument = jsonDocument;
    }

}
