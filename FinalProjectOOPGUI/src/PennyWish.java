import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PennyWish {

    private List<WishItem> wishList = new ArrayList<>();
    private PennyWiseManager pennyWiseManager;
    private String username;

    private List<WishItem> originalWishList;

    public PennyWish(PennyWiseManager pennyWiseManager, String username) {
        this.pennyWiseManager = pennyWiseManager;
        this.username = username;
        loadUserData(); // Load user data on initialization
    }

    public void loadUserData() {
        PennyWishUserData userData = pennyWiseManager.getPennyWishUserData(username);
        if (userData != null) {
            this.wishList = userData.getWishList();
            this.originalWishList = new ArrayList<>(wishList);
            JOptionPane.showMessageDialog(null, "PennyWish data loaded successfully!");
        } else {
            this.wishList = new ArrayList<>();
            this.originalWishList = new ArrayList<>(); // Initialize originalWishList
        }
    }

    public void reset() {
        wishList.clear();
    }

    public void addWish() {
        String item = JOptionPane.showInputDialog("Enter wishlist item:");
        if (item == null || item.trim().isEmpty()) {
            return;
        }
        String priceStr = JOptionPane.showInputDialog("Enter price: PHP");
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return;
        }
        try {
            double price = Double.parseDouble(priceStr);
            wishList.add(new WishItem(item, price));
            JOptionPane.showMessageDialog(null, "Item added to wishlist.");
            saveUserData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price. Please enter a valid number.");
        }
    }

    public String viewWishes() {
        if (wishList.isEmpty()) {
            return "Your wishlist is empty.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("+-----------------------------+\n");
            sb.append("|       Item       |   Price   |\n");
            sb.append("+-----------------------------+\n");
            for (WishItem item : wishList) {
                sb.append(String.format("| %-15s | %9.2f |\n", item.getName(), item.getPrice()));
            }
            sb.append("+-----------------------------+\n");
            return sb.toString();
        }
    }

    public void clearWishlist() {
        if (wishList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items in the wishlist to clear!");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the wishlist?", "Confirm Clear",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            wishList.clear();
            originalWishList.clear(); // Clear originalWishList as well
            JOptionPane.showMessageDialog(null, "Wishlist cleared!");
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(null, "Clear operation canceled.");
        }
    }

    public void removeSpecificWish() {
        if (wishList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items in the wishlist to remove!");
            return;
        }

        String itemName = JOptionPane.showInputDialog("Enter the name of the wish to remove:");

        if (itemName == null || itemName.trim().isEmpty()) {
            return;
        }

        boolean removed = wishList.removeIf(item -> item.getName().equalsIgnoreCase(itemName));

        if (removed) {
            originalWishList.removeIf(item -> item.getName().equalsIgnoreCase(itemName)); // Remove from originalWishList as well
            JOptionPane.showMessageDialog(null, "Wish removed: " + itemName);
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(null, "Wish not found: " + itemName);
        }
    }

    public String generateSummary() {
        if (wishList.isEmpty()) {
            return "Your wishlist is empty.";
        }

        double totalCost = 0;
        for (WishItem item : wishList) {
            if (item != null) { // Ensure item is not null
                totalCost += item.getPrice();
            }
        }

        String savingsPerPeriodStr = JOptionPane.showInputDialog("Enter your preferred savings amount: PHP");
        if (savingsPerPeriodStr == null || savingsPerPeriodStr.trim().isEmpty()) {
            return "No savings amount entered.";
        }

        double savingsPerPeriod;

        try {
            savingsPerPeriod = Double.parseDouble(savingsPerPeriodStr);
        } catch (NumberFormatException e) {
            return "Invalid savings amount. Please enter a valid number.";
        }

        String[] options = {"Daily", "Weekly", "Monthly"};
        String period = (String) JOptionPane.showInputDialog(null, " Select a saving period:", "Savings Period",
                JOptionPane.QUESTION_MESSAGE, null, options, options [0]);

        if (period == null) {
            return "No period selected.";
        }

        int periodsNeeded = (int) Math.ceil(totalCost / savingsPerPeriod);
        double totalSaved = savingsPerPeriod * periodsNeeded; // Calculate total savings
        double excessSavings = totalSaved - totalCost; // Calculate excess savings
        StringBuilder summary = new StringBuilder();
        summary.append("Total Wishlist Cost: PHP ").append(totalCost).append("\n");
        summary.append("You need to save PHP ").append(savingsPerPeriod).append(" ").append(period).append(" for ").append(periodsNeeded).append(" periods.\n");

        // Calculate the dates for savings
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        summary.append("Savings Dates:\n");
        for (int i = 1; i <= periodsNeeded; i++) {
            LocalDate savingDate;
            switch (period.toLowerCase()) {
                case "daily":
                    savingDate = today.plusDays(i);
                    break;
                case "weekly":
                    savingDate = today.plusWeeks(i);
                    break;
                case "monthly":
                    savingDate = today.plusMonths(i);
                    break;
                default:
                    return "Invalid period. Please select Daily, Weekly, or Monthly.";
            }
            summary.append(" - ").append(savingDate.format(formatter)).append("\n");
        }
        if (excessSavings > 0) {
            summary.append("You will have an excess of PHP ").append(excessSavings).append(" after completing your wishlist.\n");
        }

        return summary.toString();
    }

    void saveUserData() {
        PennyWishUserData userData = new PennyWishUserData();
        userData.setUsername(username);
        userData.setWishList(wishList);
        boolean hasChanges = !originalWishList.equals(wishList);

        if (hasChanges) {
            pennyWiseManager.savePennyWishUserData(username, userData);
            JOptionPane.showMessageDialog(null, "Penny Wish data saved successfully!");
        }
    }
}