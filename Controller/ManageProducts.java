package Controller;

import Model.Product;
import Model.PhysicalProduct;
import Model.DigitalProduct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageProducts implements Serializable {
    private static ManageProducts instance;
    public ArrayList<Product> productList;

    public ManageProducts() {
        productList = new ArrayList<>();
    }

    public static ManageProducts getInstance() {
        if (instance == null) {
            instance = new ManageProducts();
        }
        return instance;
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public void serializeProductsToFile(String filePath) {
        createFileIfNotExists("src/data/products.dat");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            objectOutputStream.writeObject(productList);
            System.out.println("Products have been saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save products to " + filePath);
        }
    }

    public void deserializeProductsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("src/data/products.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof Product) {
                    productList = (ArrayList<Product>) importedData;
                    System.out.println("Products have been deserialized and imported from src/data/products.dat");
                    return;
                }
            }

            System.err.println("Error: Unexpected data format in the file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void createFileIfNotExists(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + filePath);
                } else {
                    System.err.println("Error: Unable to create file " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Error: Unable to create file " + filePath);
            }
        }
    }

    public boolean addPhysicalProduct(String name, String description, int availableQuantity, double price, double weight) {
        // Allow admin to add a physical product to the system
        PhysicalProduct newProduct = new PhysicalProduct(name, description, availableQuantity, price, weight); // Initialize physical product
        productList.add(newProduct); // Add to product list
        serializeProductsToFile("src/data/products.dat");
        return true;
    }

    public boolean addDigitalProduct(String name, String description, int availableQuantity, double price) {
        // Allow admin to add a digital product to the system
        DigitalProduct newProduct = new DigitalProduct(name, description, availableQuantity, price);
        productList.add(newProduct);
        serializeProductsToFile("src/data/products.dat");
        return true;
    }

    public boolean removeProduct(String name) {
        // Allow admin to remove a product from the system
        Optional<Product> productToRemove = productList.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();

        if (productToRemove.isPresent()) {
            productList.remove(productToRemove.get());
            // Update the products to the system
            serializeProductsToFile("src/data/products.dat");
            return true;
        }

        return false;
    }

    public Product getProductByName(String name) {
        Product product = null;
        for (Product p : productList) {
            if (p.getName().equals(name)) {
                product = p;
            }
        }
        return product;
    }

    public boolean productExist(String productName) {
        // To check if a product exists
        for (Product product : getAllProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public void displayAllProducts() {
        deserializeProductsFromFile();

        List<Product> productList = getAllProducts();

        if (productList.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("There are currently " + productList.size() + " products.");

            // Display header
            System.out.printf("%-50s | %-60s | %-20s | %-10s\n", "Name", "Description", "Available Quantity", "Price");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

            // Display information
            for (Product product : productList) {
                System.out.printf("%-50s | %-60s | %-20d | $%.2f\n",
                        product.getName(), product.getDescription(), product.getAvailableQuantity(), product.getPrice());
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }
}


