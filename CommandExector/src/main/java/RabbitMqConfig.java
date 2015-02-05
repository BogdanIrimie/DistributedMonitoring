import config.ConfigExtractor;

public class RabbitMqConfig {
    private String host = null;

    public String getHost() {
        return host;
    }

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
    }
}
