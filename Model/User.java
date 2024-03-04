package Model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class User implements Serializable {
    private String userID;
    private String username;
    private String password;
    private UserRole role;

    public User(String userID, String username, String password, UserRole role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean verifyPassword(String providedPassword) {
        return providedPassword.equals(password);
    }

    public enum UserRole {
        ADMIN,
        CUSTOMER,
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User login(String username, String password) {
        // Validate user's login information
        if (this.username.equals(username) && this.password.equals(password)) {
            return this;
        }
        return null;
    }

    public String encrypt (String password) {
        // Encrypts user's password
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Converts the byte to hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}