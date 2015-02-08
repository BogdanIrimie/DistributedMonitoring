package rabbit;

import config.ConfigExtractor;

/**
 * Extract configuration data for RabbitMQ
 */
public class RabbitMqConfig {
    private String host = null;
    private String queue = null;

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
        queue = ConfigExtractor.getProperty("rabbitQueue");
    }

    public String getHost() {
        return host;
    }

    public String getQueue() {
        return queue;
    }

}
