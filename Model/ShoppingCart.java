package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart implements Comparable<ShoppingCart> {
    private final ArrayList<Product> products;
    private String cartName;
    public ShoppingCart(String cartName) {
        this.products = new ArrayList<>();
        this.cartName = cartName;
    }

    public boolean addItem(Product product, int quantity) {
        // Check if the requested quantity is available
       if (quantity > product.getAvailableQuantity()) {
           System.out.println("The requested quantity is not available for " + product.getName());
           return false;
       }

        // Check if the product is already in the cart
        if (products.contains(product)) {
            // Update the quantity if the product is already in the cart
            int existingQuantity = Collections.frequency(products, product);
            int totalQuantity = existingQuantity + quantity;
            if (totalQuantity > product.getAvailableQuantity()) {
                System.out.println("The requested quantity is not available for " + product.getName());
                return false;
            }

            // Remove existing instances of the product
            products.removeIf(p -> p.equals(product));

            // Add the product with the updated quantity
            for (int i = 0; i < quantity; i++) {
                products.add(product);
            }
        } else {
            for (int i = 0; i < quantity; i++) {
                products.add(product);
            }
        }

        System.out.println();
        return true;
    }

    public boolean removeItem(Product product) {
        if (products.contains(product)) {
            products.remove(product);
            return true;
        }
        return false;
    }

    public double cartAmount() {
        double totalAmount = 0;
        for (Product product : products) {
            totalAmount += product.getPrice();
            if (product instanceof PhysicalProduct) {
                // Add shipping fee for physical products
                double shippingFee = ((PhysicalProduct) product).getWeight() * 0.1;
                totalAmount += shippingFee;
            }
        }
        return totalAmount;
    }

    private double calculateTotalWeight() {
        // Calculate the total weight of the physical products
        double totalWeight = 0;
        for (Product product : products) {
            if (product instanceof PhysicalProduct) {
                totalWeight += ((PhysicalProduct) product).getWeight();
            }
        }
        return totalWeight;
    }

    @Override
    public int compareTo(ShoppingCart other) {
        // Implement comparison based on total weight
        double thisWeight = calculateTotalWeight();
        double otherWeight = other.calculateTotalWeight();
        return Double.compare(thisWeight, otherWeight);
    }


    // Count the quantity of a specific product in the cart
    public int getQuantity (Product product) {
        long count = products.stream().filter(p -> p.equals(product)).count();
        return (int) count;
    }

    // Update the quantity of a specific product in the cart
    public void updateQuantityInCart(Product product, int newQuantity) {
        int currentQuantity = getQuantity(product);

        // If the new quantity is greater than the current quantity, add the difference
        while (currentQuantity < newQuantity) {
            boolean added = addItem(product, newQuantity);
            if (!added) {
                return;
            }
            currentQuantity++;
        }

        while (currentQuantity > newQuantity) {
            boolean removed = removeItem(product);
            if (!removed) {
                return;
            }
            currentQuantity++;
        }

    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<Product> getCartItems() {
        return products;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public boolean containsProduct(Product product) {
        return products.contains(product);
    }
}
