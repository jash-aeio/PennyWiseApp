import java.io.Serializable;
import java.util.ArrayList;

public class ExpenseManager implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    private ArrayList<Expense> expenses;

    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public String generateExpenseReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Expense Report ------\n");
        for (Expense expense : expenses) {
            sb.append(String.format("%s: PHP %.2f\n", expense.getCategory(), expense.getAmount()));
        }
        return sb.toString();
    }
}