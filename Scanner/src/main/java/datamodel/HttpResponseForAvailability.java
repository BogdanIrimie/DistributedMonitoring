package datamodel;

public class HttpResponseForAvailability {

    private String url;
    private String httpStatusCode;
    private String body;

    public HttpResponseForAvailability() {}

    public HttpResponseForAvailability(String url, String httpStatusCode, String body) {
        this.url = url;
        this.httpStatusCode = httpStatusCode;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
