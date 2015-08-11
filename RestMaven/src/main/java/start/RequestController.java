package start;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @RequestMapping(value = "request", method = RequestMethod.POST)
    public ResponseEntity<Job> jobRequest() {
        Job job = new Job();
        job.setJobId("23");
        return new ResponseEntity<Job>(job, HttpStatus.ACCEPTED);
    }

}
