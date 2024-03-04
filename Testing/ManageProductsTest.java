package Testing;

import Controller.ManageProducts;
import Model.DigitalProduct;
import Model.PhysicalProduct;

import java.io.Serializable;

public class ManageProductsTest implements Serializable {
    public static void main(String[] args) {
        ManageProducts manageProducts = new ManageProducts();

        // Add 10 practical physical products
        manageProducts.productList.add(new PhysicalProduct("Laptop", "Powerful laptop for work and entertainment.", 20, 899.99, 2.5));
        manageProducts.productList.add(new PhysicalProduct("Bluetooth Headphones", "Wireless headphones with noise cancellation.", 30, 149.99, 0.3));
        manageProducts.productList.add(new PhysicalProduct("Smart TV", "4K Ultra HD smart television.", 15, 799.99, 25.0));
        manageProducts.productList.add(new PhysicalProduct("Fitness Tracker", "Track your health and fitness activities.", 40, 49.99, 0.1));
        manageProducts.productList.add(new PhysicalProduct("Electric Toothbrush", "Rechargeable electric toothbrush.", 25, 39.99, 0.2));
        manageProducts.productList.add(new PhysicalProduct("Robot Vacuum Cleaner", "Smart vacuum cleaner for automated cleaning.", 10, 249.99, 5.0));
        manageProducts.productList.add(new PhysicalProduct("Gaming Chair", "Ergonomic chair for gaming enthusiasts.", 18, 199.99, 15.0));
        manageProducts.productList.add(new PhysicalProduct("Portable Blender", "Blend your favorite smoothies on the go.", 35, 29.99, 0.5));
        manageProducts.productList.add(new PhysicalProduct("External Hard Drive", "Expand your storage capacity.", 22, 79.99, 0.3));
        manageProducts.productList.add(new PhysicalProduct("Drones", "Capture stunning aerial photos and videos.", 12, 299.99, 0.8));

        // Add 10 practical digital products
        manageProducts.productList.add(new DigitalProduct("Video Editing Software", "Professional video editing tool.", 15, 199.99));
        manageProducts.productList.add(new DigitalProduct("Ebook Collection", "Diverse collection of e-books.", 50, 9.99));
        manageProducts.productList.add(new DigitalProduct("Website Development Course", "Learn web development from scratch.", 25, 29.99));
        manageProducts.productList.add(new DigitalProduct("Mobile App Templates", "Ready-to-use templates for mobile app development.", 18, 49.99));
        manageProducts.productList.add(new DigitalProduct("Stock Photo Bundle", "High-quality stock photos for your projects.", 40, 14.99));
        manageProducts.productList.add(new DigitalProduct("Language Learning App Subscription", "Master a new language with interactive lessons.", 30, 19.99));
        manageProducts.productList.add(new DigitalProduct("Webinar Series Access", "Attend insightful webinars on various topics.", 20, 34.99));
        manageProducts.productList.add(new DigitalProduct("Graphic Design Software", "Create stunning graphics with professional software.", 10, 149.99));
        manageProducts.productList.add(new DigitalProduct("Online Music Streaming Service", "Unlimited access to a vast music library.", 45, 9.99));
        manageProducts.productList.add(new DigitalProduct("Virtual Reality Games Bundle", "Immersive gaming experience with VR games.", 8, 79.99));

        manageProducts.serializeProductsToFile("src/data/products.dat");
    }
}
