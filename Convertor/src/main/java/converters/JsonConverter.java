package converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonConverter {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String objectToJsonString(Object obj) {
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objToJson;
    }

    public static <T> T jsonStringToObject(String jsonString, Class<T> objClass) {
        T object = null;
        try {
            object = mapper.readValue(jsonString, objClass);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }
}
