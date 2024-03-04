package Controller;

import Model.Product;
import Model.ShoppingCart;

import java.text.SimpleDateFormat;
import java.util.*;

public class ManageShoppingCarts {
    private static ManageShoppingCarts instance;
    private final List<ShoppingCart> shoppingCarts;
    private final ManageProducts manageProducts = new ManageProducts();
    private ManageShoppingCarts() {
        shoppingCarts = Collections.synchronizedList(new ArrayList<>());
    }

    public static ManageShoppingCarts getInstance() {
        if (instance == null) {
            instance = new ManageShoppingCarts();
        }
        return instance;
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCarts;
    }

    public void createNewShoppingCart() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH.mm.ss");
        String cartName = "Shopping Cart " + dateFormat.format(new Date());

        ShoppingCart newCart = new ShoppingCart(cartName);
        shoppingCarts.add(newCart);
        System.out.println("New shopping cart '" + cartName + "' has been added.");
    }

    public boolean addToCart(ShoppingCart cart, Product product, int quantity) {
        // Check if the requested quantity is available
        if (quantity > product.getAvailableQuantity()) {
            System.err.println("Error: The requested quantity is not available for " + product.getName());
            return false;
        }

        // Remove existing instances of the product
        cart.getCartItems().removeIf(p -> p.equals(product));

        // Add the product with the updated quantity
        for (int i = 0; i < quantity; i++) {
            cart.getCartItems().add(product);
        }

        System.out.println("Success: " + quantity + " " + product.getName() + "(s) has been added to the cart " + cart.getCartName());
        return true;
    }

    public boolean removeFromCart(ShoppingCart cart, Product product) {
        return cart.removeItem(product);
    }

    public double getCartAmount(ShoppingCart cart) {
        return cart.cartAmount();
    }
}
