package View;

import Controller.ManageProducts;
import Controller.ManageShoppingCarts;
import Model.PhysicalProduct;
import Model.Product;
import Model.ShoppingCart;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    private final ManageProducts manageProducts = ManageProducts.getInstance();
    private final ManageShoppingCarts manageShoppingCarts = ManageShoppingCarts.getInstance();

    public void addProductToCartInProductView(Product product) {
        // User can add a product to a cart while viewing the product details.
        List<ShoppingCart> shoppingCarts = manageShoppingCarts.getAllShoppingCarts();

        if (shoppingCarts.isEmpty()) {
            System.out.println("No shopping carts available. Create a new cart first.");
            return;
        }

        System.out.println("Select a shopping cart to add the product: ");
        for (int i = 0; i < shoppingCarts.size(); i++) {
            System.out.printf("%d, %s\n", i + 1, shoppingCarts.get(i).getCartName());
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of the shopping cart (enter '0' to cancel): ");
        int cartChoice = scanner.nextInt();
        scanner.nextLine();

        if (cartChoice <= 0 || cartChoice > shoppingCarts.size()) {
            System.out.println("Invalid choice. Adding product to the cart has been canceled.");
            return;
        }

        System.out.println("Enter the quantity you want to add: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        ShoppingCart selectedCart = shoppingCarts.get(cartChoice - 1);

        if (quantity > product.getAvailableQuantity()) {
            // Check if the selected quantity is available
            System.err.println("The requested quantity is not available for " + product.getName());
        } else {
            manageShoppingCarts.addToCart(selectedCart, product, quantity);
        }
    }

    public void displayProductDetails(String name) {
        for (Product product : manageProducts.getAllProducts()) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("Name: " + product.getName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Available Quantity: " + product.getAvailableQuantity());
                System.out.println("Price: " + "$" + product.getPrice());
                if (product instanceof PhysicalProduct) {
                    System.out.println("Weight: " + ((PhysicalProduct) product).getWeight() + " kg");
                }
                break;
            }
        }
    }
}
