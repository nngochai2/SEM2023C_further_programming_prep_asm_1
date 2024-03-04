package Model;

import java.io.Serializable;

public class PhysicalProduct extends Product implements Gift, Serializable {
    private double weight;
    private String giftMessage;
    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight) {
        super(name, description, availableQuantity, price);
        this.weight = weight;
        this.giftMessage = null;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public void setMessage(String msg) {
        this.giftMessage = msg;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String toString() {
        return "PHYSICAL - " + super.toString();
    }
}
