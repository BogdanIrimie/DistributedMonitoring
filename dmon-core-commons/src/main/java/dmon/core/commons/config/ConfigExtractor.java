package dmon.core.commons.config;

import dmon.core.commons.helpers.ProgramArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Extract configuration/properties from conf.properties
 */
public class ConfigExtractor {
    private static final Logger logger = LoggerFactory.getLogger(ConfigExtractor.class);
    private static Properties configProp;
    private static ConfigExtractor configInstance = null;

    /**
     * Use the config file from /etc,
     * if there is non then use the standard config file
     */
    private ConfigExtractor() {
        String configFile = ProgramArguments.getConfigFile();
        String configFileFallback = "/conf.properties";
        File conf = null;

        InputStream in = getClass().getResourceAsStream(configFileFallback);
        configProp = new Properties();

        if (configFile != null) {
            conf = new File(configFile);
        }

        try {
            if (conf != null && conf.exists() && conf.isFile()) {
                configProp.load(new FileInputStream(configFile));
            }
            else {
                configProp.load(in);
            }
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

    /**
     * Reset configuration, this will cause the reload of the configuration file.
     */
    public static void resetConfiguration() {
        configInstance = new ConfigExtractor();
    }

}
