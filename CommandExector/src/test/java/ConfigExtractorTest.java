import config.ConfigExtractor;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;

public class ConfigExtractorTest {

    @Test
    public void getProperties() {
        int propertyCount = 0;
        Enumeration<String> properties = ConfigExtractor.getPropertyList();

        while (properties.hasMoreElements()) {
            properties.nextElement();
            propertyCount++;
        }

        assertTrue(propertyCount > 0);
    }

    @Test
    public void testValueForAllProperties() {
        Enumeration<String> properties = ConfigExtractor.getPropertyList();
        boolean allPropertiesInitialized = true;

        while (properties.hasMoreElements()) {
            String propertyName = properties.nextElement();
            String propertyValue = ConfigExtractor.getProperty(propertyName);
            if (propertyValue ==  null) {
                allPropertiesInitialized = false;
            }
        }

        assertTrue(allPropertiesInitialized);
    }

}
