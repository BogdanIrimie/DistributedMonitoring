package convertors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodel.Measurement;

import java.io.IOException;

/**
 * Convert to and from Job to Measurement
 */
public class MeasurementConverter {
    private static ObjectMapper mapper= new ObjectMapper();

    /**
     * Convert measurement object to json string
     *
     * @param measurement contain measurement data
     * @return json representation of measurement object
     */
    public static String measurementToJsonString(Measurement measurement) {
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(measurement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objToJson;
    }

    /**
     * Converts json string to a measurement object
     *
     * @param measurementString representation of measurement object in json format
     * @return measurement object obtained from json string
     */
    public static Measurement jsonStringToMeasurement(String measurementString) {
        Measurement measurement = null;
        try {
            measurement =  mapper.readValue(measurementString, Measurement.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return measurement;
    }

}
