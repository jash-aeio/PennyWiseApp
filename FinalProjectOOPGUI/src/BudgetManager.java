import java.io.Serializable;
import java.util.ArrayList;

public class BudgetManager implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    private ArrayList<BudgetItem> budgetItems;

    public BudgetManager() {
        budgetItems = new ArrayList<>();
    }

    public void addBudgetItem(BudgetItem item) {
        budgetItems.add(item);
    }

    public ArrayList<BudgetItem> getBudgetItems() {
        return budgetItems; // This return statement is necessary
    }
}