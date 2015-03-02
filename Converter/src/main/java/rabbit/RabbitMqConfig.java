package rabbit;

import config.ConfigExtractor;

/**
 * Extract configuration data for RabbitMQ
 */
public class RabbitMqConfig {
    private String host = null;
    private String receiveQueue = null;
    private String sendQueue = null;

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
        receiveQueue = ConfigExtractor.getProperty("rabbitReceiveQueue");
        sendQueue = ConfigExtractor.getProperty("rabbitSendQueue");
    }

    public String getHost() {
        return host;
    }

    public String getReceiveQueue() {
        return receiveQueue;
    }

    public String getSendQueue() {
        return sendQueue;
    }
}
