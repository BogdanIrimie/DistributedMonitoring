import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        InputStream in = getClass().getResourceAsStream(configFile);
        Properties configProp = new Properties();
        try {
            configProp.load(in);
            ip = configProp.getProperty("mongoHost");
            port =  Integer.parseInt(configProp.getProperty("mongoPort"));

        } catch (NumberFormatException nfe) {
            System.err.println("Port is not a integer, please check config.properties.");
            nfe.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
