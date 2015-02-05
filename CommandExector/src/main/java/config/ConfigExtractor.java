package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigExtractor {
    private final String configFile = "conf.properties";
    private static Properties configProp;
    private static ConfigExtractor configInstance = null;

    private ConfigExtractor() {
        InputStream in = getClass().getResourceAsStream(configFile);
        configProp = new Properties();

        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getProperty(String property) {
        if (configInstance == null) {
            new ConfigExtractor();
        }

        return configProp.getProperty(property);
    }
}
