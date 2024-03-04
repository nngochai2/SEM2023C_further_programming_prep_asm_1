package Testing;

import Controller.ManageUser;
import Model.Admin;
import Model.Customer;
import Model.User;
import View.Main;

import java.io.Serializable;

public class ManageUserTest implements Serializable {
    public static void main(String[] args) {
        ManageUser manageUser = new ManageUser();

        User admin = new Admin("1", "admin 1", "adminPassword", User.UserRole.ADMIN);
        manageUser.users.add(admin);

        User customer = new Customer("2", "user 1", "userPassword", User.UserRole.CUSTOMER);
        manageUser.users.add(customer);

        manageUser.serializeUsers("src/data/users.dat");
    }
}
