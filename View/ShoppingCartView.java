package View;
import Controller.ManageProducts;
import Controller.ManageShoppingCarts;
import Model.Product;
import Model.ShoppingCart;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ShoppingCartView {
    private ShoppingCart currentCart;
    private final ManageProducts manageProducts = ManageProducts.getInstance();
    private final ManageShoppingCarts manageShoppingCarts = ManageShoppingCarts.getInstance();

    public void displayShoppingCartDetails(ShoppingCart cart) {
        System.out.println("______________________________________________________________________CUSTOMER - SHOPPING CART DETAILS_________________________________________________________________");
        System.out.println("Cart Name: " + cart.getCartName());

        if (cart.isEmpty()) {
            System.out.println("No item in the cart.");
        } else {
            System.out.println("Items in your shopping cart:");

            // Display header
            System.out.printf("%-20s | %-40s | %-10s\n", "Name", "Description", "Price");
            System.out.println("--------------------------------------------------");

            for (Product product : cart.getCartItems()) {
                System.out.printf("%-20s | %-40s | $%.2f\n",
                        product.getName(), product.getDescription(), product.getPrice());
            }
            System.out.println("--------------------------------------------------");

            System.out.printf("Total Cart Amount: $%.2f\n", manageShoppingCarts.getCartAmount(cart));
        }

        while (true) {
            System.out.println("--------------------------------------------------");
            Scanner scanner = new Scanner(System.in);
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. Add A Product To Cart");
            System.out.println("2. Remove A Product From Cart");
            System.out.println("3. Delete This Cart");
            System.out.println("4. Cancel");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.addProductToCart();
                case 2 -> this.removeProductFromCart();
                case 3 -> this.deleteCurrentCart();
                case 4 -> {
                    return;
                }
            }
        }
    }

    // Allows user to add a product to cart in shopping cart view
    public void addProductToCart() {
        System.out.println("______________________________________________________________________CUSTOMER - ADD PRODUCT TO CART______________________________________________________________________");

        List<Product> allProducts = manageProducts.getAllProducts();

        if (allProducts.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        // Display all available products
        displayAvailableProducts(allProducts);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the product you want to add to your shopping cart (enter 'cancel' to cancel: ");
        String name = scanner.nextLine().trim();

        if (name.equalsIgnoreCase("cancel")) {
            System.out.println("Adding product to the cart has been canceled.");
            return;
        }

        // Find the selected product

        // Product selectedProduct = findProductByName(name, allProducts);

        Product selectedProduct = manageProducts.getProductByName(name);

        // Get valid quantity that user want to buy
        int quantity = getValidQuantity(scanner);


        if (quantity > 0 && quantity <= Objects.requireNonNull(selectedProduct).getAvailableQuantity()) {
            List<ShoppingCart> shoppingCarts = manageShoppingCarts.getAllShoppingCarts();
            ShoppingCart customerCart = shoppingCarts.get(0);
            boolean addedToCart = manageShoppingCarts.addToCart(customerCart, selectedProduct, quantity);

            if (addedToCart) {
                System.out.println("Product has been added to the shopping cart successfully.");
            } else {
                System.out.println("Failed to add the product to the shopping cart. Either the product is not available or it's already in your cart.");
            }

        } else {
            assert selectedProduct != null;
            System.out.println("The required quantity is not available for " + selectedProduct.getName() + "at the moment.");
        }
    }

    private void displayAvailableProducts(List<Product> products) {
        // Display available products
        System.out.println("Available products:");
        System.out.printf("%-20s | %-40s | %-20s | %-10s\n", "Name", "Description", "Available Quantity", "Price");
        System.out.println("----------------------------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-20s | %-40s | %-20d | $%.2f\n",
                    product.getName(), product.getDescription(), product.getAvailableQuantity(), product.getPrice());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    private int getValidQuantity(Scanner scanner) {
        int quantity;
        while (true) {
            try {
                System.out.println("What is the quantity that you want to buy?: ");
                quantity = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return quantity;
    }

//    private Product findProductByName(String name, List<Product> products) {
//        for (Product product : products) {
//            if (product.getName().equalsIgnoreCase(name)) {
//                return product;
//            }
//        }
//        return null;
//    }

    public void removeProductFromCart() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the product to remove (enter 'cancel' to cancel): ");
        String productName = scanner.nextLine();

        if (productName.equalsIgnoreCase("cancel")) {
            System.out.println("Removing product from cart has been canceled.");
            return;
        }

        Product product = manageProducts.getProductByName(productName);

        if (product != null) {
            boolean removed = manageShoppingCarts.removeFromCart(currentCart, product);
            if (removed) {
                System.out.println("Product has been removed successfully.");
            } else {
                System.out.println("Failed to remove this product.");
            }
        } else {
            System.out.println("Product not found. Please check the product name and try again.");
        }
    }

    public void deleteCurrentCart() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you sure you want to delete current cart? (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            boolean removed = manageShoppingCarts.getAllShoppingCarts().remove(currentCart);
            if (removed) {
                System.out.println("Current cart has been deleted successfully.");
                currentCart = null;

                CustomerView customerView = new CustomerView();
                customerView.viewAllShoppingCarts();
            } else {
                System.out.println("Failed to delete this cart.");
            }
        } else if (confirmation.equals("no")) {
            System.out.println("Deleting the current cart has been canceled.");
        } else {
            System.out.println("Invalid input.");
        }
    }
}
