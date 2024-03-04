package Model;

import java.io.Serializable;

public class DigitalProduct extends Product implements Serializable {
    public DigitalProduct(String name, String description, int availableQuantity, double price) {
        super(name, description, availableQuantity, price);

    }

    @Override
    public String toString() {
        return "DIGITAL - " + super.toString();
    }
}
