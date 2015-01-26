import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by constantin on 26.01.2015.
 */
public class ExecuteCommand {
    public static void main(String[] args) {
        String line;
        Process p;
        try {
            p = Runtime.getRuntime().exec("nmap 194.102.63.152");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
