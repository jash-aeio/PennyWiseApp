import java.io.*;
import java.util.HashMap;

public class PennyWiseManager {
    private HashMap<String, PennyWiseUserData> pennyWiseUserDataMap = new HashMap<>();
    private HashMap<String, PennyWishUserData> pennyWishUserDataMap = new HashMap<>();
    private static final String PENNY_WISE_DATA_FILE = "pennywise_userdata.ser";
    private static final String PENNY_WISH_DATA_FILE = "pennywish_userdata.ser";

    public PennyWiseManager() {
        loadPennyWiseUserData();
        loadPennyWishUserData();
    }

    // PennyWise User Data methods
    public PennyWiseUserData getPennyWiseUserData(String username) {
        return pennyWiseUserDataMap.get(username);
    }

    public void savePennyWiseUserData(String username, PennyWiseUserData userData) {
        pennyWiseUserDataMap.put(username, userData);
        savePennyWiseToFile();
    }

    private void loadPennyWiseUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PENNY_WISE_DATA_FILE))) {
            pennyWiseUserDataMap = (HashMap<String, PennyWiseUserData>) ois.readObject();
        } catch (FileNotFoundException e) {
            pennyWiseUserDataMap = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void savePennyWiseToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PENNY_WISE_DATA_FILE))) {
            oos.writeObject(pennyWiseUserDataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // PennyWish User Data methods
    public PennyWishUserData getPennyWishUserData(String username) {
        return pennyWishUserDataMap.get(username);
    }

    public void savePennyWishUserData(String username, PennyWishUserData userData) {
        pennyWishUserDataMap.put(username, userData);
        savePennyWishToFile();
    }

    private void loadPennyWishUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PENNY_WISH_DATA_FILE))) {
            pennyWishUserDataMap = (HashMap<String, PennyWishUserData>) ois .readObject();
        } catch (FileNotFoundException e) {
            pennyWishUserDataMap = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void savePennyWishToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PENNY_WISH_DATA_FILE))) {
            oos.writeObject(pennyWishUserDataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}