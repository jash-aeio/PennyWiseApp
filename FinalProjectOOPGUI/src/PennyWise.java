
import javax.swing.*;
import java.util.ArrayList;

public class PennyWise {

    private ArrayList<BudgetItem> budgetItems = new ArrayList<>();
    private double totalBudget;
    private PennyWiseManager pennyWiseManager;
    private String username;

    private ArrayList<BudgetItem> originalBudgetItems;
    private double originalTotalBudget;

    public PennyWise(PennyWiseManager pennyWiseManager, String username) {
        this.pennyWiseManager = pennyWiseManager;
        this.username = username;
        loadUserData(); // Load user data on initialization
    }

    public void loadUserData() {
        PennyWiseUserData userData = pennyWiseManager.getPennyWiseUserData(username);
        if (userData != null) {
            this.budgetItems = (ArrayList<BudgetItem>) userData.getBudgetItems();
            this.totalBudget = userData.getTotalBudget();
            this.originalBudgetItems = new ArrayList<>(budgetItems);
            this.originalTotalBudget = totalBudget;
            JOptionPane.showMessageDialog(null, "PennyWise data loaded successfully!");
        } else {
            this.budgetItems = new ArrayList<>();
            this.totalBudget = 0;
            this.originalBudgetItems = new ArrayList<>();
            this.originalTotalBudget = 0;
        }
    }

    public void reset() {
        budgetItems.clear();
        totalBudget = 0;
    }

    public void setBudget() {
        String input = JOptionPane.showInputDialog("Enter your total budget: PHP");
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        try {
            totalBudget = Double.parseDouble(input);
            JOptionPane.showMessageDialog(null, "Budget set to PHP " + totalBudget);
            saveUserData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }

    public void addExpense() {
        String category = JOptionPane.showInputDialog("Enter expense category:");
        if (category == null || category.trim().isEmpty()) {
            return;
        }
        String amountStr = JOptionPane.showInputDialog("Enter amount: PHP");
        try {
            double amount = Double.parseDouble(amountStr);
            budgetItems.add(new Expense(category, amount));
            JOptionPane.showMessageDialog(null, "Expense added.");
            saveUserData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
        }
    }

    public String viewExpenses() {
        if (budgetItems.isEmpty()) {
            return "No expenses recorded yet.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("+------------------------------+\n");
            sb.append("|       Category    |   Amount  |\n");
            sb.append("+------------------------------+\n");
            for (BudgetItem item : budgetItems) {
                Expense expense = (Expense) item;
                sb.append(String.format("| %-15s | %9.2f |\n", expense.getCategory(), expense.getAmount()));
            }
            sb.append("+------------------------------+\n");
            return sb.toString();
        }
    }

    public String generateSummary() {
        double totalExpenses = 0;
        for (BudgetItem item : budgetItems) {
            if (item != null) { // Ensure item is not null
                totalExpenses += item.calculateCost();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n------ Budget Summary ------\n");
        sb.append(String.format("Total Budget: PHP %.2f\n", totalBudget));
        sb.append(String.format("Total Expenses: PHP %.2f\n", totalExpenses));
        sb.append(String.format("Remaining Budget: PHP %.2f\n", totalBudget - totalExpenses));
        sb.append("\n--- Expense Breakdown ---\n");
        if (budgetItems.isEmpty()) {
            sb.append("No expenses recorded.\n");
        } else {
            for (BudgetItem item : budgetItems) {
                if (item != null) { // Ensure item is not null
                    String name = item.getName() != null ? item.getName() : "Unknown"; // Handle null names
                    sb.append(String.format("%-15s: PHP %.2f\n", name, item.calculateCost()));
                }
            }
        }
        sb.append("----------------------------\n");
        return sb.toString();
    }

    public void clearAllExpenses() {

        if (budgetItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No expenses to clear!");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all expenses?", "Confirm Clear",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            budgetItems.clear();
            JOptionPane.showMessageDialog(null, "All expenses cleared!");
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(null, "Clear operation canceled.");
        }
    }

    public void removeSpecificExpense() {
        if (budgetItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No expenses to remove!");
            return;
        }
        
        String category = JOptionPane.showInputDialog("Enter the category of the expense to remove:");

        if (category == null || category.trim().isEmpty()) {
            return;
        }

        boolean removed = budgetItems.removeIf(item -> item.getName().equals(category));
        if (removed) {
            JOptionPane.showMessageDialog(null, "Expense removed: " + category);
            saveUserData();
        } else {
            JOptionPane.showMessageDialog(null, "Expense not found: " + category);
        }
    }

    void saveUserData() {
        PennyWiseUserData userData = new PennyWiseUserData();
        userData.setUsername(username);
        userData.setBudgetItems(budgetItems);
        userData.setTotalBudget(totalBudget);
        boolean hasChanges = !originalBudgetItems.equals(budgetItems) || originalTotalBudget != totalBudget;

        if (hasChanges) {
            pennyWiseManager.savePennyWiseUserData(username, userData);
            JOptionPane.showMessageDialog(null, "Penny Wise data saved successfully!");
        }
    }
}
