package View;

import java.util.Scanner;

/**
 * @author Nguyen Ngoc Hai
 * 
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("=============================================================== WELCOME TO SHOPPING APPLICATION! ===============================================================");

        int input;
        do {
            System.out.println("Please login (enter '0' to cancel):");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Customer");
            System.out.println("Enter your choice: ");
            input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1 -> {
                    AdminView adminView = new AdminView();
                    adminView.menu();
                }
                case 2 -> {
                    CustomerView customerView = new CustomerView();
                    customerView.menu();
                }
            }
        } while (input != 0);
    }
}