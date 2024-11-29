import java.io.Serializable;

public abstract class BudgetItem implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    public abstract String getName();
    public abstract double calculateCost();
}