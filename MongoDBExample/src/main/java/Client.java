import com.mongodb.*;

import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {

        MongoManager mm = new MongoManager();
        String id = mm.saveJson("{\"id\":20, name:\"Bogdan\"}");
        String jsonRepresentation = mm.retrieveJsonById(id);
        System.out.println(jsonRepresentation);
        mm.closeConnection();

    }
}

