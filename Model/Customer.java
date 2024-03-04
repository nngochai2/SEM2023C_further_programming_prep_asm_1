package Model;

import java.io.Serializable;

public class Customer extends User implements Serializable {

    public Customer(String userID, String username, String password, UserRole role) {
        super(userID, username, password, role);
    }
}
