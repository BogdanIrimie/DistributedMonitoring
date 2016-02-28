package datamodel;

import dmon.core.commons.datamodel.MongoId;
import org.junit.Test;

public class MongoIdTest {

    /**
     * Test constructor, getters and setters.
     */
    @Test
    public void createModelIdObjects() {
        MongoId mongoId = new MongoId();

        mongoId.set$oid("123");
        mongoId.get$oid();
    }
}
