package datamodel;

import dmon.core.commons.datamodel.Measurement;
import dmon.core.commons.datamodel.MongoId;
import org.junit.Test;

public class MeasurementTest {

    /**
     * Test constructor, getters and setters.
     */
    @Test
    public void createMeasurementObjects() {
        Measurement measurement = new Measurement();
        new Measurement("13", "security tls 192.168.56.110", "192.168.56.102");
        new Measurement("13", "192.168.56.100", "security tls 192.168.56.110", "192.168.56.102",
                new String[]{
                        "XmlToJsonConverter",
                        "security.TlsCiphersuitesFilter",
                        "security.TlsEcrypt2Level"},
                "adapters.EventHubAdapter");

        measurement.set_id(new MongoId());
        measurement.get_id();

        measurement.setClientId("13");
        measurement.getClientId();

        measurement.setClientIp("192.168.56.102");
        measurement.getClientIp();

        measurement.setUserCommand("security tls 192.168.56.110");
        measurement.getUserCommand();

        measurement.setResponseAddress("192.168.56.100");
        measurement.getResponseAddress();

        measurement.setProcessors(
                new String[]{
                        "XmlToJsonConverter",
                        "security.TlsCiphersuitesFilter",
                        "security.TlsEcrypt2Level"});
        measurement.getProcessors();

        measurement.setAdapter("adapters.EventHubAdapter");
        measurement.getAdapter();

        measurement.setRawResult("...");
        measurement.getRawResult();

        measurement.setProcessedResult("...");
        measurement.getProcessedResult();
    }
}
