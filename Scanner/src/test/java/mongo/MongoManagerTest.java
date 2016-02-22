package mongo;

import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Measurement;
import dmon.core.commons.mongo.MongoManager;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MongoManagerTest {

    @Test
    public void instantiateMongoManagerManyTimes() {
        for (int i = 0; i < 10; i++) {
            new MongoManager();
        }
    }

    @Test
    public void closeConnectionMultipleTimes() {
        MongoManager mm = new MongoManager();
        for (int i = 0; i < 10; i++) {
            mm.closeConnection();
        }
    }

    @Test
    public void testJsonPushAndPull() {
        MongoManager mm = new MongoManager();
        Measurement measurement = new Measurement();
        String serializedMeasurement = JsonConverter.objectToJsonString(measurement);
        String id = mm.pushJson(serializedMeasurement);

        serializedMeasurement = serializedMeasurement.replaceFirst("null", "\"" + id + "\"").replaceAll("\\s+", "");
        String retrievedMeasurementString = mm.pullJsonById(id);
        retrievedMeasurementString = retrievedMeasurementString
                .replace("{ \"$oid\" : \"" + id + "\"}", "\"" + id + "\"")
                .replaceAll("\\s+", "");
        assertTrue(serializedMeasurement.equals(retrievedMeasurementString));
    }

    @Test
    public void pullJsonWithWrongId() {
        MongoManager mm = new MongoManager();

        try {
            mm.pullJsonById("-23");
        } catch (Exception e) {
            // it should throw a exception because id does not exist in db
            assertTrue(true);
        }
    }

}
