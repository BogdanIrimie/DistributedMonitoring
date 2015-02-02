import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjToJsonConvertor {
    public static String map(Job job) {
        ObjectMapper mapper = new ObjectMapper();
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(job);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objToJson;
    }
}
