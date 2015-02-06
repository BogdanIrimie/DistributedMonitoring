package mongo;

import config.ConfigExtractor;

public class MongoDbConfig {
    private final String configFile = "conf.properties";
    private String ip;
    private int port;

    /**
     * read configuration data for mongoDB
     */
    public MongoDbConfig() {
        ip = ConfigExtractor.getProperty("mongoHost");
        try {
            port = Integer.parseInt(ConfigExtractor.getProperty("mongoPort"));
        }
        catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
