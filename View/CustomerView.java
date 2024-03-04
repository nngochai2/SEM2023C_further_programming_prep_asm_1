package View;

import Controller.ManageProducts;
import Controller.ManageShoppingCarts;
import Model.PhysicalProduct;
import Model.Product;
import Model.ShoppingCart;

import java.util.*;

public class CustomerView {
    private final ManageProducts manageProducts;
    private final ManageShoppingCarts manageShoppingCarts;
    private final ShoppingCartView shoppingCartView = new ShoppingCartView();
    private final ProductView productView = new ProductView();

    public CustomerView() {
        manageProducts = ManageProducts.getInstance();
        manageShoppingCarts = ManageShoppingCarts.getInstance();
    }
    public void menu() {
        manageProducts.deserializeProductsFromFile();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===================================================================== WELCOME CUSTOMER =============================================================================");
            System.out.println("PLEASE CHOOSE ONE OF THE FOLLOWING OPTIONS: ");
            System.out.println("1. View Products");
            System.out.println("2. View Shopping Carts");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> this.viewAllProducts();
                case 2 -> this.viewAllShoppingCarts();
                case 3 -> this.checkout();
                case 4 -> {
                    System.out.println("Exiting customer view. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void viewAllProducts() {
        // Display all products in the system
        System.out.println("_________________________________________________________________CUSTOMER - PRODUCTS - ALL PRODUCTS______________________________________________________________________");

        manageProducts.displayAllProducts();

        Scanner scanner = new Scanner(System.in);

        // Providing sorting options
        System.out.println("\nSort products by: ");
        System.out.printf("%-25s | %-25s | %-25s\n", "1. Price (Low to High)", "2. Price (High to Low)", "3. Cancel");
        System.out.println("Enter your choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        if (sortChoice != 3) {
            // Sort products
            List<Product> sortedProducts = sortProducts(sortChoice);

            // Display sorted products
            System.out.println("_________________________________________________________________CUSTOMER - PRODUCTS - ALL PRODUCTS______________________________________________________________________");
            displaySortedProducts(sortedProducts);
        }

        System.out.println("Enter a name of a product to view further details (enter 'cancel' to cancel): ");
        String productName = scanner.nextLine();

        if (!productName.equalsIgnoreCase("cancel")) {
            Product product = manageProducts.getProductByName(productName);

            if (product != null) {
                productView.displayProductDetails(productName);
                System.out.println("Do you want to add this product to a cart? (yes/no): ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    productView.addProductToCartInProductView(product);
                } else if (choice.equalsIgnoreCase("no")) {
                    System.out.println("Exiting product view...");
                } else {
                    System.out.println("Invalid input. Exiting product view...");
                }
            } else {
                System.out.println("Product not found.");
            }
        }
    }

    private List<Product> sortProducts(int sortChoice) {
        // Allow users to sort the products by price
        List<Product> sortedProducts = new ArrayList<>(manageProducts.getAllProducts());
        switch (sortChoice) {
            case 1 -> {
                sortedProducts.sort(Comparator.comparing(Product::getPrice));
            }
            case 2 ->  {
                sortedProducts.sort(Comparator.comparing(Product::getPrice).reversed());
            }
            default -> {
                System.out.println("Invalid choice. Sorting by default order.");
            }
        }
        return sortedProducts;
    }

    private void displaySortedProducts(List<Product> sortedProducts) {
        // Display header
        System.out.printf("%-50s | %-60s | %-20s | %-10s\n", "Name", "Description", "Available Quantity", "Price");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

        // Display information
        for (Product product : sortedProducts) {
            System.out.printf("%-50s | %-60s | %-20d | $%.2f\n",
                    product.getName(), product.getDescription(), product.getAvailableQuantity(), product.getPrice());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    public void viewAllShoppingCarts() {
        // Display all shopping carts
        List<ShoppingCart> shoppingCarts = manageShoppingCarts.getAllShoppingCarts();

        if (shoppingCarts.isEmpty()) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("No carts available.");
            int input;
            while (true){
                System.out.println("Please choose one of the following options (enter 'cancel' to cancel): ");
                System.out.println("1. Create New Shopping Cart");
                System.out.println("2. Exit");
                input = scanner.nextInt();
                scanner.nextLine();

                switch (input) {
                    case 1 -> createNewCart();
                    case 2 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Invalid input. Please try again.");
                    }
                }
            }
        }

        System.out.println("_________________________________________________________________CUSTOMER - PRODUCTS - ALL SHOPPING CARTS_________________________________________________________________");
        System.out.println("Your shopping carts: ");
        System.out.printf("%-5s | %-20s\n", "Index", "Cart Name");
        System.out.println("----------------------------------------");

        for (int i = 0; i < shoppingCarts.size(); i++) {
            System.out.printf("%-5d | %-20s\n", i + 1, shoppingCarts.get(i).getCartName());
        }

        System.out.println("----------------------------------------");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View A Shopping Cart");
            System.out.println("2. Create New Shopping Cart");
            System.out.println("3. Delete A Cart");
            System.out.println("4. Exit");
            System.out.println("-----------------------------------------------");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    // User can choose a shopping cart to view details
                    System.out.println("Enter the index number of the shopping cart (enter '0' to cancel): ");
                    int selectedIndex = scanner.nextInt();

                    if (selectedIndex == 0) {
                        System.out.println("Viewing shopping cart details has been canceled.");
                    } else if (selectedIndex > 0 && selectedIndex <= shoppingCarts.size()) {
                        ShoppingCart selectedCart = shoppingCarts.get(selectedIndex - 1);
                        shoppingCartView.displayShoppingCartDetails(selectedCart);

                    } else {
                        System.out.println("Invalid index number. Viewing shopping cart details has been canceled.");
                    }
                }
                case 2 -> this.createNewCart();
                case 3 -> this.deleteACart();
                case 4 -> {
                    System.out.println("Exiting the shopping carts menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createNewCart() {
        manageShoppingCarts.createNewShoppingCart();
    }

    public void deleteACart() {
        List<ShoppingCart> shoppingCarts = manageShoppingCarts.getAllShoppingCarts();

        if (shoppingCarts.isEmpty()) {
            System.out.println("No carts available to delete.");
            return;
        }

        // Ask user to choose a shopping cart to delete
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index number of the shopping cart to delete (enter '0' to cancel): ");
        int selectedIndex = scanner.nextInt();
        scanner.nextLine();

        if (selectedIndex == 0) {
            System.out.println("Deleting a shopping cart has been canceled.");
        } else if (selectedIndex > 0 && selectedIndex <= shoppingCarts.size()) {
            ShoppingCart selectedCart = shoppingCarts.get(selectedIndex -1);
            shoppingCarts.remove(selectedCart);
            System.out.println("Shopping cart has been deleted successfully.");
        } else {
            System.out.println("Invalid index number. Deleting a shopping cart has been canceled.");
        }
    }

    public void checkout() {
        List<ShoppingCart> shoppingCarts = manageShoppingCarts.getAllShoppingCarts();

        // Check if user have any shopping cart
        if (shoppingCarts.isEmpty()) {
            System.out.println("No shopping carts available to checkout.");
            return;
        }

        System.out.println("=============================================================== CUSTOMER - PRODUCTS - CHECKOUT ===============================================================");

        if (shoppingCarts.size() == 1) {
            // If there is only one shopping cart, proceed directly to check out
            ShoppingCart shoppingCart = shoppingCarts.get(0);

            System.out.println("______________________________________________________________________CUSTOMER - SHOPPING CART DETAILS_________________________________________________________________");
            System.out.println("Cart Name: " + shoppingCart.getCartName());

            if (shoppingCart.isEmpty()) {
                System.out.println("No item in the cart.");
            } else {
                System.out.println("Items in your shopping cart:");

                // Display header
                System.out.printf("%-20s | %-40s | %-10s\n", "Name", "Description", "Price");
                System.out.println("--------------------------------------------------");

                for (Product product : shoppingCart.getCartItems()) {
                    System.out.printf("%-20s | %-40s | %-20d | $%.2f\n",
                            product.getName(), product.getDescription(), product.getAvailableQuantity(), product.getPrice());
                }
                System.out.println("--------------------------------------------------");

                System.out.printf("Total Cart Amount: $%.2f\n", manageShoppingCarts.getCartAmount(shoppingCart));
            }

            // Calculate the shipping fee
            calculateShippingFee(shoppingCart);

            performCheckout(shoppingCart);

        } else {
            System.out.println("Your shopping carts: ");
            System.out.printf("%-5s | %-20s\n", "Index", "Cart Name");
            System.out.println("-------------------------------");

            for (int i = 0; i < shoppingCarts.size(); i++) {
                System.out.printf("%-5d | %-20s\n", i + 1, shoppingCarts.get(i).getCartName());
            }

            System.out.println("-------------------------------");

            Scanner scanner = new Scanner(System.in);

            // Ask use to choose one of the shopping carts to check out
            System.out.println("Enter the index number of the shopping cart (enter '0' to cancel): ");
            int selectedIndex = scanner.nextInt();
            scanner.nextLine();

            if (selectedIndex == 0) {
                System.out.println("Check out has been canceled.");
            } else if (selectedIndex > 0 && selectedIndex < shoppingCarts.size()) {
                ShoppingCart selectedCart = shoppingCarts.get(selectedIndex);
                shoppingCartView.displayShoppingCartDetails(selectedCart);
                calculateShippingFee(selectedCart);

                performCheckout(selectedCart);
            } else {
                System.out.println("Invalid index number. Checkout has been canceled.");
            }
        }
    }

    private void calculateShippingFee(ShoppingCart cart) {
        double totalWeight = 0.0;

        for (Product product : cart.getCartItems()) {
            if (product instanceof PhysicalProduct) {
                totalWeight += ((PhysicalProduct) product).getWeight();
            }
        }

        System.out.println("Shipping fee: " + (totalWeight * 0.1));
    }

    private void performCheckout(ShoppingCart shoppingCart) {
        List<Product> purchasedProducts = shoppingCart.getCartItems();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to proceed with the checkout? (yes/no): ");
        String confirmation = scanner.nextLine();

        // Asking for confirmation to check out
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Payment successful!");
            double totalAmount = manageShoppingCarts.getCartAmount(shoppingCart);
            System.out.printf("Total cart amount: $%.2f\n", totalAmount);

            // Clear the cart after successful checkout
            shoppingCart.getCartItems().clear();
            System.out.println("Your shopping cart has been cleared.");

            // Count the purchase quantities
            Map<String, Integer> purchasedQuantities = new HashMap<>();
            for (Product product : purchasedProducts) {
                String productName = product.getName();
                purchasedQuantities.put(productName, purchasedQuantities.getOrDefault(productName, 0) + 1);
            }

            // Update the available quantities
            for (Map.Entry<String, Integer> entry : purchasedQuantities.entrySet()) {
                String productName = entry.getKey();
                int purchasedQuantity = entry.getValue();

                updateProductQuantities(productName, purchasedQuantity);
            }
        } else {
            System.out.println("Checkout has been canceled.");
        }
    }

    public void updateProductQuantities(String productName, int purchasedQuantity) {
        Product product = manageProducts.getProductByName(productName);

        if (product != null) {
            // Update the available quantity
            int currentQuantity = product.getAvailableQuantity();
            product.setAvailableQuantity(currentQuantity - purchasedQuantity);

            // Serialize the products to update the available quantities
            manageProducts.serializeProductsToFile("src/data/products.dat");
        }
    }
}
