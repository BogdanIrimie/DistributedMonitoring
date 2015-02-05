public class Client {
    public static void main(String[] args) {

        MongoManager mm = new MongoManager();
        String id = mm.pushJson("{\"id\":20, name:\"Bogdan\"}");
        String jsonRepresentation = mm.pullJsonById(id);
        System.out.println(jsonRepresentation);
        mm.closeConnection();

    }
}

