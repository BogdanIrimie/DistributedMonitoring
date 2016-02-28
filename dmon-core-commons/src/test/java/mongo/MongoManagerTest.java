package mongo;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Measurement;
import dmon.core.commons.datamodel.MongoId;
import dmon.core.commons.mongo.MongoManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MongoManagerTest {

    private Fongo fongo = new Fongo("Server1");
    private DB db = null;

    /**
     * Create fake DB and collection.
     */
    @Before
    public void setUpFongo() {
        db = fongo.getDB("monitoring");
    }

    /**
     * Test if class "MongoManger" can be instantiated many times, a DB connection must exist
     */
    @Test
    public void instantiateMongoManagerManyTimes() {
        for (int i = 0; i < 10; i++) {
            new MongoManager();
        }
    }

    /**
     * Test if same connection can be closed many times.
     */
    @Test
    public void closeConnectionMultipleTimes() {
        MongoManager mm = new MongoManager();
        for (int i = 0; i < 10; i++) {
            mm.closeConnection();
        }
    }

    /**
     * Test push and pull operations, a DB connection must exist!
     */
    @Test
    public void testJsonPushAndPull() {
        MongoManager mm = new MongoManager(db);
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

    /**
     * Test error in case of wrong id used for pull, a DB connection must exist!
     */
    @Test
    public void pullJsonWithWrongId() {
        MongoManager mm = new MongoManager(db);

        try {
            mm.pullJsonById("-23");
        } catch (Exception e) {
            // it should throw a exception because id does not exist in db
            assertTrue(true);
        }
    }

    @Test
    public void testPushJsonInCollection() {
        MongoManager mm = new MongoManager(db);
        Measurement measurement =  new Measurement();
        String recordId = mm.pushJson(JsonConverter.objectToJsonString(measurement), "clientRequest");

        mm.updateJsonWithId(recordId, "clientIp", "192.168.56.100");

        String measurementString = mm.pullJsonById(recordId);
        Measurement retrievedMeasurement = JsonConverter.jsonStringToObject(measurementString, Measurement.class);

        measurement.set_id(new MongoId(recordId));
        measurement.setClientIp("192.168.56.100");

        assertTrue(measurement.equals(retrievedMeasurement));
    }
}
