
import java.io.*;
import java.util.HashMap;

public class SaveUser {

    private final String userDataFile;

    public SaveUser(String userDataFile) {
        this.userDataFile = userDataFile;
    }

    public void save(HashMap<String, String> users) {
        new File("data").mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userDataFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
