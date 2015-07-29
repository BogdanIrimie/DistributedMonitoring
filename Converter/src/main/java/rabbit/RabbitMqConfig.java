package rabbit;

import config.ConfigExtractor;

/**
 * Extract configuration data for RabbitMQ
 */
public class RabbitMqConfig {
    private String host = null;
    private String receiveQueue = null;
    private String preseneterSendQueue = null;
    private String remediatorSendQueue = null;
    private String username = null;
    private String password = null;

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
        receiveQueue = ConfigExtractor.getProperty("rabbitReceiveQueue");
        preseneterSendQueue = ConfigExtractor.getProperty("rabbitSendQueue");
        remediatorSendQueue = ConfigExtractor.getProperty("rabbitSendQueue2");
        username = ConfigExtractor.getProperty("rabbitUser");
        password = ConfigExtractor.getProperty("rabbitPassword");
    }

    public String getHost() {
        return host;
    }

    public String getReceiveQueue() {
        return receiveQueue;
    }

    public String getPreseneterSendQueue() {
        return preseneterSendQueue;
    }

    public String getRemediatorSendQueue() {
        return remediatorSendQueue;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
