package Model;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private String name;
    private String description;
    private int availableQuantity;
    private double price;

    public Product(String name, String description, int availableQuantity, double price) {
        this.name = name;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", price=" + price +
                '}';
    }
}
