package executors;

import datamodel.HttpResponseForAvailability;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequestExecutor {

    public HttpResponseForAvailability checkStatus(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);
        HttpResponseForAvailability httpResponseForAvailability = new HttpResponseForAvailability(response.getStatusCode().toString(), response.getBody().toString());


        return httpResponseForAvailability;
    }

}
