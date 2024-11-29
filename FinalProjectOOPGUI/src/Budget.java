import java.io.Serializable;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    private double totalBudget;

    public Budget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }
}