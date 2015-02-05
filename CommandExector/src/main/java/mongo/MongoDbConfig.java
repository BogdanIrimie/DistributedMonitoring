package mongo;

import config.ConfigExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MongoDbConfig {
    private final String configFile = "conf.properties";
    private String ip;
    private int port;

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
}
