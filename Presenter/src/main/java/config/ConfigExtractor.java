package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Extract configuration/properties  from conf.properties
 */
public class ConfigExtractor {
    private static final Logger logger = LoggerFactory.getLogger(ConfigExtractor.class);
    private final String configFile = "../conf.properties";
    private static Properties configProp;
    private static ConfigExtractor configInstance = null;

    private ConfigExtractor() {
        InputStream in = getClass().getResourceAsStream(configFile);
        configProp = new Properties();

        try {
            configProp.load(in);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * Read a property value from the config file
     *
     * @param property we want to find value for
     * @return value of property
     */
    public static String getProperty(String property) {
        if (configInstance == null) {
            configInstance = new ConfigExtractor();
        }

        return configProp.getProperty(property);
    }


    /**
     * Retrieve a enumeration with all properties.
     * @return properties
     */
    public static Enumeration<String> getPropertyList() {
        if (configInstance == null) {
            configInstance = new ConfigExtractor();
        }

        return (Enumeration<String>)configProp.propertyNames();
    }

}
