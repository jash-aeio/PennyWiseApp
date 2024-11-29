
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PennyWishUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private List<WishItem> wishList;

    public PennyWishUserData() {
        wishList = new ArrayList<>();
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<WishItem> getWishList() {
        return wishList;
    }

    public void setWishList(List<WishItem> wishList) {
        this.wishList = wishList;
    }
}
