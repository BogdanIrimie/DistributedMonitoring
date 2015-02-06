package convertors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodel.Measurement;

import java.io.IOException;

public class MeasurementConvertor {
    private static ObjectMapper mapper= new ObjectMapper();;

    public static String measurementToJsonString(Measurement measurement) {
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(measurement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objToJson;
    }

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
