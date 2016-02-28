package dmon.core.commons.mongo;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Manage interactions with DB
 */
public class MongoManager {
    private static final Logger logger = LoggerFactory.getLogger(MongoManager.class);
    private MongoClient mongoClient = null;
    private DBCollection results = null;
    private static MongoDbConfig mongoConfig = new MongoDbConfig();
    private DB db;

    public MongoManager() {
        this(mongoConfig.getIp());
    }


    public MongoManager(String ip) {
        this(ip, mongoConfig.getPort());
    }

    /**
     * Specify the Ip and the port for communicating with MongoDB
     *
     * @param ip used by MongoDB
     * @param port used by MongoDB
     */
    public MongoManager(String ip, int port) {
        mongoClient = new MongoClient(ip, port);
        logger.info("Connection to DB was established.");
        db = mongoClient.getDB("monitoring");
        results = db.getCollection("clientRequest");
    }

    /**
     * Provide the database that will be used by MongoManager.
     *
     * @param db database that will be used by MongoManager.
     */
    public MongoManager(DB db) {
        this.db = db;
        results = db.getCollection("clientRequest");
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
     * Save json in a defined collection.
     *
     * @param jsonString to be saved in db
     * @param collectionName where to save the json
     * @return id of the new record
     */
    public String pushJson(String jsonString, String collectionName) {
        Object jsonObj = JSON.parse(jsonString);
        DBObject dbObj = (DBObject)jsonObj;
        db.getCollection(collectionName).insert(dbObj);
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
     * Update a given field for a record with a given id
     *
     * @param id of the record that will be updated
     * @param field that will be updated
     * @param value new value for field
     */
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
            logger.info("Connection to DB was closed.");
        }
    }

}
