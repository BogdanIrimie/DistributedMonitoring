package rabbit;

import config.ConfigExtractor;

/**
 * Extract configuration data for RabbitMQ
 */
public class RabbitMqConfig {
    private String host = null;
    private String queue = null;
    private String username = null;
    private String password = null;

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
        queue = ConfigExtractor.getProperty("rabbitSendQueue");
        username = ConfigExtractor.getProperty("rabbitUser");
        password = ConfigExtractor.getProperty("rabbitPassword");
    }

    public String getHost() {
        return host;
    }

    public String getQueue() {
        return queue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
