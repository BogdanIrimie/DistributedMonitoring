import java.util.UUID;

public class Job {

    private String uuid;
    private String command;
    private String linkToXml;
    private String linkToJson;

    public Job(String command) {
        uuid = UUID.randomUUID().toString().replace("-", "");
        this.command = command;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getLinkToXml() {
        return linkToXml;
    }

    public void setLinkToXml(String linkToXml) {
        this.linkToXml = linkToXml;
    }

    public String getLinkToJson() {
        return linkToJson;
    }

    public void setLinkToJson(String linkToJson) {
        this.linkToJson = linkToJson;
    }
}
