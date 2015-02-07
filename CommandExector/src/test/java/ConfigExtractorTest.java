import config.ConfigExtractor;
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

}
