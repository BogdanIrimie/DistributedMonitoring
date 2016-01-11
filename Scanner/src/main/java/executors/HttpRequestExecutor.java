package executors;

import datamodel.HttpResponseForAvailability;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Make a HTTP request and obtain results
 */
public class HttpRequestExecutor {

    /**
     * Make a HTTP request and return status code and message body
     * @param url to use for HTTP request
     * @return object containing HTTP status code and message body
     */
    public HttpResponseForAvailability checkStatus(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);
        HttpResponseForAvailability httpResponseForAvailability = new HttpResponseForAvailability(url, response.getStatusCode().toString(), response.getBody().toString());
        return httpResponseForAvailability;
    }

}
