
import java.io.*;
import java.util.HashMap;

public class LoadUser {

    private final String userDataFile;

    public LoadUser(String userDataFile) {
        this.userDataFile = userDataFile;
    }

    public HashMap<String, String> load() {
        HashMap<String, String> users = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userDataFile))) {
            users = (HashMap<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("User  data file not found. A new one will be created upon signup.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
