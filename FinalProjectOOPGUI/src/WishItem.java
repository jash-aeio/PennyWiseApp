import java.io.Serializable;

public class WishItem implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    private String name;
    private double price;

    public WishItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}