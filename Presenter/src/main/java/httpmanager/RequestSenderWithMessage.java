package httpmanager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RequestSenderWithMessage {

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
            System.out.println(responseString.toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
