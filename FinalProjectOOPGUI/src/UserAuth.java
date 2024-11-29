import javax.swing.*;
import java.util.HashMap;

public class UserAuth {

    private HashMap<String, String> users;
    private final String userDataFile = "data/users.dat";
    private String currentUsername; // Variable to store the current username

    public UserAuth() {
        LoadUser  loadUser  = new LoadUser (userDataFile);
        users = loadUser .load();
    }

    public void signUp() {
        String username = JOptionPane.showInputDialog("Enter username:");

        JPasswordField passwordField = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists. Try a different username.");
            } else {
                users.put(username, password);
                SaveUser  saveUser  = new SaveUser (userDataFile);
                saveUser .save(users);
                JOptionPane.showMessageDialog(null, "Signup successful.");
            }
        }
    }

    public boolean login() {
        String username = JOptionPane.showInputDialog("Enter username:");

        JPasswordField passwordField = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, passwordField, "Enter password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                currentUsername = username; // Store the current username
                JOptionPane.showMessageDialog(null, "Login successful.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
                return false;
            }
        } else {
            return false; // If the user cancels the password input
        }
    }

    public String getCurrentUsername() {
        return currentUsername; // Return the stored username
    }
}