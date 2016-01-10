package mongo;

import config.ConfigExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extract configuration data for MongoDB
 */
public class MongoDbConfig {
    private static final Logger logger = LoggerFactory.getLogger(MongoDbConfig.class);
    private final String configFile = "conf.properties";
    private String ip;
    private int port;

    /**
     * Read configuration data for mongoDB
     */
    public MongoDbConfig() {
        ip = ConfigExtractor.getProperty("mongoHost");
        try {
            port = Integer.parseInt(ConfigExtractor.getProperty("mongoPort"));
        }
        catch (NumberFormatException nfe) {
            logger.error(nfe.getMessage(), nfe);
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
