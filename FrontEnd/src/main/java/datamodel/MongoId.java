package datamodel;

/**
 * Model ID from MongoDb
 */
public class MongoId {
    String $oid;

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

}
