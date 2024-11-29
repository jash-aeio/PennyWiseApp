
import javax.swing.*;
import java.util.List;

public class BudgetView {

    public void displayBudgetSummary(List<BudgetItem> budgetItems, double totalBudget) {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Budget Summary ------\n");
        sb.append(String.format("Total Budget: PHP %.2f\n", totalBudget));
        sb.append("Expenses:\n");

        for (BudgetItem item : budgetItems) {
            if (item instanceof Expense) {
                Expense expense = (Expense) item;
                sb.append(String.format("%s: PHP %.2f\n", expense.getName(), expense.getAmount()));
            }
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Budget Summary", JOptionPane.INFORMATION_MESSAGE);
    }
}
