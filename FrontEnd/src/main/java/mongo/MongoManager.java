package mongo;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

/**
 * Manage interactions with DB
 */
public class MongoManager {
    private MongoClient mongoClient = null;
    private DBCollection results = null;
    private DB db = null;
    private static MongoDbConfig mongoConfig = new MongoDbConfig();

    public MongoManager() {
        this(mongoConfig.getIp());
    }


    public MongoManager(String ip) {
        this(ip, mongoConfig.getPort());
    }

    public MongoManager(String ip, int port) {
        try {
            mongoClient = new MongoClient(ip, port);
            System.out.println("Connecton was established!");
            db = mongoClient.getDB("monitoringResults");
            results = db.getCollection("testData");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a json in the DB.
     *
     * @param jsonString the json to be saved in the db
     * @return the ID of the saved object
     */
    public String pushJson(String jsonString) {
        Object jsonObj = JSON.parse(jsonString);
        DBObject dbObj = (DBObject)jsonObj;
        results.insert(dbObj);
        return dbObj.get("_id").toString();
    }

    /**
     * Query for a Json using id.
     *
     * @param id id of the record in db
     * @return Json with the db data for the provided id
     */
    public String pullJsonById(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = results.findOne(query);
        return dbObj.toString();
    }

    /**
     * Close connection to DB
     */
    public void closeConnection() {
        if (mongoClient!= null) {
            mongoClient.close();
            System.out.println("Connection was closed!");
        }
    }

}
