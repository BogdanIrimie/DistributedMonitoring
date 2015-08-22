package httpserver;

import converters.JsonConverter;
import datamodel.Request;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RequestSender {
    private String url;
    private String query;

    public RequestSender() {
    }

    public String sendRequest(String url, Request request) {
        String charset = StandardCharsets.UTF_8.name();
        String requestJsonString = JsonConverter.objectToJsonString(request);
        String requestParameter = "request=" + requestJsonString;


        try {
            String encodedRequest = URLEncoder.encode(requestParameter, charset);

            URLConnection connection = new URL(url + "?" + encodedRequest).openConnection();
            connection.setConnectTimeout(64000); // timeout set to 64 seconds
            connection.setRequestProperty("Accept-Charset", charset);

            InputStream response = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(response));
            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }
            response.close();
            // System.out.println(responseString.toString());
            return responseString.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
