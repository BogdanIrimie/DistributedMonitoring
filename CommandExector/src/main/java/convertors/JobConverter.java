package convertors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodel.Job;

import java.io.IOException;

/**
 * Convert to and from Job to Json
 */
public class JobConverter {
    private static ObjectMapper mapper= new ObjectMapper();;

    /**
     * Convert Job object to json.
     *
     * @param job that will be converted to json
     * @return String with json representation of Job
     */
    public static String jobToJsonString(Job job) {
        String objToJson = null;
        try {
            objToJson = mapper.writeValueAsString(job);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objToJson;
    }

    /**
     * Convert Json to Job object.
     *
     * @param jobString a json representing a Job object
     * @return a job object extracted from the json
     */
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
