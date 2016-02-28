package converter;

import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Job;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class JsonConverterTest {

    public static final Logger logger = LoggerFactory.getLogger(JsonConverterTest.class);

    /**
     * Test converting objects to stings.
     */
    @Test
    public void convertObjectToJsonString() {
        String object = JsonConverter.objectToJsonString(new Job("13"));
        final String jsonRepresentation = "{\"id\":\"13\"}";
        assertTrue(object.equals(jsonRepresentation));
    }

    /**
     * Test converting strings to objects.
     */
    @Test
    public void convertJsonStringToObject() {
        final String jsonRepresentation = "{\"id\":\"13\"}";
        Job deserializedJob = JsonConverter.jsonStringToObject(jsonRepresentation, Job.class);
        String id = deserializedJob.getId();
        assertTrue("13".equals(id));
    }

    /**
     * Test if exception is thrown when string cannot be converted to object.
     */
    @Test
    public void jsonStringException() {
        logger.error("The following exception is normal, we wanted to generate it!");
        JsonConverter.jsonStringToObject("...", Job.class);
    }

}
