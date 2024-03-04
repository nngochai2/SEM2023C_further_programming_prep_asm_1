package View;

import Controller.ManageProducts;
import Model.DigitalProduct;
import Model.PhysicalProduct;
import Model.Product;
import java.util.Scanner;

public class AdminView {
    private final ManageProducts manageProducts = ManageProducts.getInstance();
    private final ProductView productView = new ProductView();
    public void menu() {
        manageProducts.deserializeProductsFromFile(); // Deserialize the products
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("========================================================================= WELCOME ADMIN =========================================================================");
            System.out.println("You can choose one of the following options ");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> this.addProduct();
                case 2 -> this.productsView();
                case 3 -> {
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void productsView() {
        // Display products view
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("________________________________________________________________________ADMIN - PRODUCTS_____________________________________________________________________________");
            System.out.println("There are currently " + manageProducts.getAllProducts().size() + " products.");

            System.out.println("You can choose one of the following actions: ");
            System.out.println("0. GO BACK");
            System.out.println("1. List All Products");
            System.out.println("2. Find Product By Name");
            System.out.println("3. Add A Product");
            System.out.println("4. Remove A Product");
            System.out.println("5. Edit A Product");
            System.out.println("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> this.menu();
                case 1 -> this.listAllProducts();
                case 2 -> this.findProductByName();
                case 3 -> this.addProduct();
                case 4 -> this.removeProduct();
                case 5 -> this.editProduct();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void listAllProducts() {
        System.out.println("______________________________________________________________________ADMIN - PRODUCTS - ALL PRODUCTS______________________________________________________________________________________");
        manageProducts.displayAllProducts();
    }

    public void findProductByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________ADMIN - PRODUCTS - FIND PRODUCTS_____________________________________________________________________________________");
        String input;

        do {
            System.out.println("(Enter '0' to cancel.)");
            System.out.println("Enter product name: ");
            input = scanner.nextLine().trim();

            Product product = manageProducts.getProductByName(input);
            if (product != null) {
                productView.displayProductDetails(product.getName());
            } else {
                System.out.println("No product was found.");
            }
        } while (!input.equals("0"));

        // Return to the products view
        this.productsView();
    }

    public void addProduct() {
        // Allow admin to add a product to the system
        Scanner scanner = new Scanner(System.in);

        System.out.println("______________________________________________________________________ADMIN - PRODUCTS - ADD PRODUCT____________________________________________________________________________________");
        System.out.println("Please enter the product information");

        System.out.println("Enter new product name (enter 'cancel' to cancel the procedure): ");
        String name = scanner.nextLine();

        // Check if there is already an existing product with the same name
        if (name.equalsIgnoreCase("cancel") || manageProducts.productExist(name)) {
            if (name.equalsIgnoreCase("cancel")) {
                System.out.println("Product creation canceled.");
            } else {
                System.out.println("A product with the same name already exists.");
            }
            return;
        }

        System.out.println("Enter the product description: ");
        String description = scanner.nextLine();

        System.out.println("Enter the available quantity: ");
        int availableQuantity = scanner.nextInt();

        System.out.println("Enter the product price: ");
        double price = scanner.nextDouble();

        System.out.println("1. Create Physical Product");
        System.out.println("2. Create Digital Product");
        System.out.println("3. Cancel");
        System.out.println("Enter your choice: ");
        int productTypeChoice = scanner.nextInt();
        scanner.nextLine();

        switch (productTypeChoice) {
            case 1 -> {
                System.out.println("Enter the weight for physical product: ");
                double weight = scanner.nextDouble();
                if (manageProducts.addPhysicalProduct(name, description, availableQuantity, price, weight)) {
                    System.out.println("Product has been added successfully.");
                } else {
                    System.err.println("Error: Unable to add the product. Please try again.");
                }
            }

            case 2 -> {
                if (manageProducts.addDigitalProduct(name, description, availableQuantity, price)) {
                    System.out.println("Digital product has been added successfully");
                } else {
                    System.err.println("Error: Unable to add the product. Please try again.");
                }
            }

            case 3 -> {
                System.out.println("Product creation has been canceled.");
            }

            default -> {
                System.out.println("Invalid choice. Product creation has been canceled.");
            }
        }
    }

    public void editProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("______________________________________________________________________ADMIN - PRODUCTS - EDIT PRODUCT____________________________________________________________________________________");
        System.out.println("Enter the name of the product to edit: ");
        String name = scanner.nextLine();

        // Check if the product exists
        Product productToEdit = manageProducts.getProductByName(name);
        if (productToEdit == null) {
            System.out.println("Product not found.");
            return;
        }

        // Display current details of the product
        System.out.println("Product found:");
        productView.displayProductDetails(name);

        // Editing product names has been avoided, since the names in this project represent as IDs, which can be troublesome when they are editable.

        // Allow the user to edit specific details (Except for the name due to major complexities)
        System.out.println("Enter new description (enter '-' to keep current information): ");
        String newDescription = scanner.nextLine().trim();
        if (!newDescription.equals("-")) {
            productToEdit.setDescription(newDescription);
        }

        System.out.println("Enter new available quantity (enter -1 to keep current information)");
        int newAvailableQuantity = scanner.nextInt();
        scanner.nextLine();
        if (newAvailableQuantity != -1) {
            productToEdit.setAvailableQuantity(newAvailableQuantity);
        }

        System.out.println("Enter new price (enter -1.0 to keep current information): ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();
        if (newPrice != -1.0) {
            productToEdit.setPrice(newPrice);
        }

        if (productToEdit instanceof PhysicalProduct) {
            System.out.println("Enter the new weight for physical product (enter -1.0 to keep current information):");
            double newWeight = scanner.nextDouble();
            scanner.nextLine();
            if (newWeight != -1.0) {
                ((PhysicalProduct) productToEdit).setWeight(newWeight);
            }
        }

        System.out.println("Do you want to save your changes (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            manageProducts.serializeProductsToFile("src/data/products.dat"); // Updating the product
            System.out.println("Product has been edited successfully.");
        }

    }

    public void removeProduct() {
        // Allow admin to remove products from the system
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________ADMIN - PRODUCTS - REMOVE PRODUCT________________________________________________________________________");

        String productName;
        do {
            System.out.println("Enter 'cancel' to cancel the procedure.");
            System.out.println("Enter product name: ");
            productName = scanner.nextLine();

            if (manageProducts.productExist(productName) ) {
                // Display product details
                System.out.println("Product found: ");
                productView.displayProductDetails(productName);

                // Asking for user confirmation
                System.out.println("Do you want to remove this product? (yes/no): ");
                String confirmation = scanner.nextLine().toLowerCase();
                
                if (confirmation.equals("yes")) {
                    boolean removed = manageProducts.removeProduct(productName);
                    if (removed) {
                        System.out.println("The product has been removed successfully.");
                    } else {
                        System.out.println("Product removal failed.");
                    }
                } else {
                    System.out.println("Removal has been canceled.");
                }
                return;
            }
        } while (!productName.equals("cancel"));
    }
}
