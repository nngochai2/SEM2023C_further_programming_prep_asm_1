package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Admin extends User implements Serializable {
    private ArrayList<Product> products;

    public Admin(String userID, String username, String password, UserRole role) {
        super(userID, username, password, role);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public Admin login(String username, String password) {
        return (Admin) super.login(username, password);
    }
}
