package mongo;

import dmon.core.commons.config.ConfigExtractor;
import dmon.core.commons.mongo.MongoDbConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConfigExtractor.class)
public class MongoDbConfigTest {

    /**
     * Test if Ip is valid.
     */
    @Test
    public void testGetIp() {
        MongoDbConfig mongoConfig = new MongoDbConfig();
        String ip = mongoConfig.getIp();

        final String ipPattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
        assertTrue(ip.matches(ipPattern));
    }

    /**
     * Test if port is valid.
     */
    @Test
    public void testGetPort() {
        MongoDbConfig mongoDbConfig = new MongoDbConfig();
        int port = mongoDbConfig.getPort();
        assertTrue(port >=0 && port <= 65535);
    }

    /**
     * Test if setting IP works.
     */
    @Test
    public void testSetIp() {
        String ip = "192.168.64.111";
        MongoDbConfig mongoDbConfig = new MongoDbConfig();
        mongoDbConfig.setIp(ip);
        assertTrue(mongoDbConfig.getIp().equals(ip));
    }

    /**
     * Test if setting the port works.
     */
    @Test
    public void testSetPort() {
        int port = 27017;
        MongoDbConfig mongoDbConfig = new MongoDbConfig();
        mongoDbConfig.setPort(port);
        assertTrue(mongoDbConfig.getPort() == port);
    }

    /**
     * Test exception of port number format error.
     */
    @Test
    public void testConfigException() {
        String portProperty = "mongoPort";
        PowerMockito.mockStatic(ConfigExtractor.class);
        BDDMockito.given(ConfigExtractor.getProperty(portProperty)).willReturn("a3b");

        // This should throw an exception because mongoPort is not a number.
        MongoDbConfig mongoDbConfig = new MongoDbConfig();
    }
}
