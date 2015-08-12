package httpserver;

import converters.JsonConverter;
import datamodel.Request;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RequestSenderWithMessage {
    private final static String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    public RequestSenderWithMessage() {}

    public static String sendRequest(String url, Request request) {
        try {
            String requestJsonString = JsonConverter.objectToJsonString(request);
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);   // post
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            OutputStream output = connection.getOutputStream();
            output.write(requestJsonString.getBytes(charset));

            InputStream response = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(response));
            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }
            return responseString.toString();
        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
        } catch (MalformedURLException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return "...";
    }
}
