package mongo;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoManager {
    private static final Logger logger = LoggerFactory.getLogger(MongoManager.class);
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
            System.out.println("Connection was established!");
            db = mongoClient.getDB("monitoringResults");
            results = db.getCollection("testData");
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
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

    public void updateJsonWithId(String id, String field,  String value) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        BasicDBObject updateDocument = new BasicDBObject();
        updateDocument.append("$set", new BasicDBObject().append(field, value));

        results.update(query, updateDocument);
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
