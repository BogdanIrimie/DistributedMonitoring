import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JobConverter {
    private static ObjectMapper mapper= new ObjectMapper();;

    public static String jobToJsonString(Job job) {
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(job);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objToJson;
    }

    public static Job jsonStringToJob(String jobString) {
        Job  job = null;
        try {
            job =  mapper.readValue(jobString, Job.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return job;
    }
}
