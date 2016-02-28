package dmon.core.commons.datamodel;

/**
 * Model ID from MongoDb
 */
public class MongoId {
    private String $oid;

    public MongoId() {

    }

    public MongoId(String $oid) {
        this.$oid = $oid;
    }

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MongoId mongoId = (MongoId) o;

        return $oid != null ? $oid.equals(mongoId.$oid) : mongoId.$oid == null;

    }
}
