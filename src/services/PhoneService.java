package services;

import models.Phone;
import database.DatabaseHelper;
import java.util.List;

public class PhoneService {
    private DatabaseHelper db;

    public PhoneService() {
        this.db = new DatabaseHelper();
    }

    // Add new phone
    public void addNewPhone(String brand, String model, String imei, double price, String status) {
        if (brand.isEmpty() || model.isEmpty() || imei.isEmpty()) {
            System.out.println("❌ Error: Brand, Model, and IMEI cannot be empty!");
            return;
        }

        if (price <= 0) {
            System.out.println("❌ Error: Price must be greater than 0!");
            return;
        }

        Phone phone = new Phone(brand, model, imei, price, status);
        db.addPhone(phone);
    }

    // Display all phones
    public void displayAllPhones() {
        List<Phone> phones = db.getAllPhones();
        
        if (phones.isEmpty()) {
            System.out.println("❌ No phones in inventory!");
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println("📱 ALL PHONES IN INVENTORY");
        System.out.println("=".repeat(100));
        System.out.printf("%-5s %-15s %-15s %-20s %-15s %-15s %-12s%n", 
                "ID", "Brand", "Model", "IMEI", "Price (TZS)", "Status", "Date Added");
        System.out.println("-".repeat(100));

        for (Phone phone : phones) {
            System.out.printf("%-5d %-15s %-15s %-20s %-15.0f %-15s %-12s%n",
                    phone.getId(),
                    phone.getBrand(),
                    phone.getModel(),
                    phone.getImei(),
                    phone.getPrice(),
                    phone.getStatus(),
                    phone.getDateAdded());
        }
        System.out.println("=".repeat(100) + "\n");
    }

    // Display phone by ID
    public void displayPhoneById(int id) {
        Phone phone = db.getPhoneById(id);
        
        if (phone == null) {
            System.out.println("❌ Phone with ID " + id + " not found!");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📱 PHONE DETAILS");
        System.out.println("=".repeat(60));
        System.out.println("ID:          " + phone.getId());
        System.out.println("Brand:       " + phone.getBrand());
        System.out.println("Model:       " + phone.getModel());
        System.out.println("IMEI:        " + phone.getImei());
        System.out.println("Price:       " + phone.getPrice() + " TZS");
        System.out.println("Status:      " + phone.getStatus());
        System.out.println("Date Added:  " + phone.getDateAdded());
        System.out.println("=".repeat(60) + "\n");
    }

    // Edit phone
    public void editPhone(int id, String brand, String model, String imei, double price, String status) {
        if (brand.isEmpty() || model.isEmpty() || imei.isEmpty()) {
            System.out.println("❌ Error: Brand, Model, and IMEI cannot be empty!");
            return;
        }

        if (price <= 0) {
            System.out.println("❌ Error: Price must be greater than 0!");
            return;
        }

        Phone phone = new Phone(id, brand, model, imei, price, status, "");
        db.updatePhone(id, phone);
    }

    // Delete phone
    public void deletePhone(int id) {
        Phone phone = db.getPhoneById(id);
        if (phone == null) {
            System.out.println("❌ Phone with ID " + id + " not found!");
            return;
        }
        
        db.deletePhone(id);
    }

    // Search phones
    public void searchPhones(String keyword) {
        List<Phone> phones = db.searchPhones(keyword);
        
        if (phones.isEmpty()) {
            System.out.println("❌ No phones found matching: " + keyword);
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println("🔍 SEARCH RESULTS FOR: " + keyword);
        System.out.println("=".repeat(100));
        System.out.printf("%-5s %-15s %-15s %-20s %-15s %-15s %-12s%n", 
                "ID", "Brand", "Model", "IMEI", "Price (TZS)", "Status", "Date Added");
        System.out.println("-".repeat(100));

        for (Phone phone : phones) {
            System.out.printf("%-5d %-15s %-15s %-20s %-15.0f %-15s %-12s%n",
                    phone.getId(),
                    phone.getBrand(),
                    phone.getModel(),
                    phone.getImei(),
                    phone.getPrice(),
                    phone.getStatus(),
                    phone.getDateAdded());
        }
        System.out.println("=".repeat(100) + "\n");
    }

    // Get inventory statistics
    public void displayStatistics() {
        int totalPhones = db.getTotalPhones();
        int availablePhones = db.getAvailablePhones();
        int soldPhones = totalPhones - availablePhones;

        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 INVENTORY STATISTICS");
        System.out.println("=".repeat(50));
        System.out.println("Total Phones:       " + totalPhones);
        System.out.println("Available:          " + availablePhones);
        System.out.println("Sold:               " + soldPhones);
        System.out.println("=".repeat(50) + "\n");
    }

    // Mark phone as sold
    public void markAsSold(int id) {
        Phone phone = db.getPhoneById(id);
        if (phone == null) {
            System.out.println("❌ Phone with ID " + id + " not found!");
            return;
        }
        
        phone.setStatus("Sold");
        db.updatePhone(id, phone);
        System.out.println("✅ Phone marked as Sold!");
    }

    // Close database
    public void closeDatabase() {
        db.closeConnection();
    }
}
