package Controller;

import Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageUser implements Serializable {
    private int lastUserID;
    private static ManageUser instance;
    public ArrayList<User> users = new ArrayList<>();
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = this.users.stream()
                .filter(user1 -> user1.getUsername().equals(username))
                .findFirst();
        if (user.isPresent() && user.get().verifyPassword(password)) {
            return user;
        } else {
            return Optional.empty();
        }
    }

    public void serializeUsers(String filePath) {
        createFileIfNotExists("src/data/users.dat");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            objectOutputStream.writeObject(users);
            System.out.println("Users have been serialized and saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error! Unable to serialize users to " + filePath);
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
                System.err.println("Error: Unable to create new file " + filePath);
            }
        }
    }

    public void deserializeUsersFromFile(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            users = (ArrayList<User>) objectInputStream.readObject();
            System.out.println("Users have been deserialized and imported from " + filePath);

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ManageProducts.class.getName()).log(Level.SEVERE, "Error during deserialization", e);
            System.err.println("Error: Unable to deserialize users from " + filePath);
        }
    }
}
