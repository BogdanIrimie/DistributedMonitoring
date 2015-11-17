package datamodel;

public class HttpResponseForAvailability {

    private String statusCode;
    private String body;

    public HttpResponseForAvailability() {}

    public HttpResponseForAvailability(String statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
