package services;

import models.User;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private Map<String, User> users; // Simple in-memory storage for Phase 1
    
    public AuthenticationService() {
        this.users = new HashMap<>();
        initializeDefaultUsers();
    }
    
    // Initialize with default users for testing
    private void initializeDefaultUsers() {
        // Default users for Phase 1 (in production, use database and password hashing)
        users.put("admin", new User(1, "admin", "admin123", "admin@ephonetech.com", "Admin", "2026-05-25"));
        users.put("manager", new User(2, "manager", "manager123", "manager@ephonetech.com", "Manager", "2026-05-25"));
        users.put("staff", new User(3, "staff", "staff123", "staff@ephonetech.com", "Staff", "2026-05-25"));
    }
    
    // Login user
    public User login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Username and password cannot be empty!");
            return null;
        }
        
        User user = users.get(username);
        
        if (user == null) {
            System.out.println("❌ Invalid username!");
            return null;
        }
        
        if (!user.getPassword().equals(password)) {
            System.out.println("❌ Invalid password!");
            return null;
        }
        
        System.out.println("✅ Login successful!");
        return user;
    }
    
    // Register new user (for Phase 2)
    public boolean registerUser(String username, String password, String email, String role) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            System.out.println("❌ All fields are required!");
            return false;
        }
        
        if (users.containsKey(username)) {
            System.out.println("❌ Username already exists!");
            return false;
        }
        
        if (password.length() < 6) {
            System.out.println("❌ Password must be at least 6 characters!");
            return false;
        }
        
        User newUser = new User(username, password, email, role);
        users.put(username, newUser);
        System.out.println("✅ User registered successfully!");
        return true;
    }
    
    // Logout user
    public void logout() {
        System.out.println("✅ Logged out successfully!");
    }
    
    // Display user info
    public void displayUserInfo(User user) {
        if (user == null) {
            System.out.println("❌ No user logged in!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("👤 USER INFORMATION");
        System.out.println("=".repeat(50));
        System.out.println("Username:   " + user.getUsername());
        System.out.println("Email:      " + user.getEmail());
        System.out.println("Role:       " + user.getRole());
        System.out.println("Date Created: " + user.getDateCreated());
        System.out.println("=".repeat(50) + "\n");
    }
}
