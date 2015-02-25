package converter;

import converters.JsonConverter;
import datamodel.Job;
import datamodel.Measurement;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JsonConverterTest {

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

}
