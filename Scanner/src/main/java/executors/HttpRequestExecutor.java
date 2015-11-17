package executors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequestExecutor {

    public String checkStatus(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);
        return "statusCode: " + response.getStatusCode() +"\nbody: " + response.getBody();
    }

}
