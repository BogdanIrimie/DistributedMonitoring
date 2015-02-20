package httpserver;

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

    public static void sendRequest(String url, String id, String command, String responseAddress) {
        String charset = StandardCharsets.UTF_8.name();
        String query = null;

        try {
            query = String.format("id=%s&command=%s&responseAddress=%s",
                    URLEncoder.encode(id, charset),
                    URLEncoder.encode(command, charset),
                    responseAddress != null ? URLEncoder.encode(responseAddress, charset) : "");

            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(response));
            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }
            System.out.println(responseString.toString());

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
