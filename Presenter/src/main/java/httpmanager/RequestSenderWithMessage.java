package httpmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RequestSenderWithMessage {

    private static final Logger logger = LoggerFactory.getLogger(RequestSenderWithMessage.class);
    private final static String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    public RequestSenderWithMessage() {}

    public static void sendRequest(String url, String message) {
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);   // post
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            OutputStream output = connection.getOutputStream();
            output.write(message.getBytes(charset));

            InputStream response = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(response));
            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }
            logger.info("Respons from destination: " + responseString.toString());

        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
