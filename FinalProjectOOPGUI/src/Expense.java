import java.io.Serializable;

public class Expense extends BudgetItem implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    private String category;
    private double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return category; // Assuming category is the name for the expense
    }

    @Override
    public double calculateCost() {
        return amount;
    }
}