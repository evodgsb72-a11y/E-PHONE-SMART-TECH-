package database;

import models.Phone;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:database/phone_shop.db";
    private Connection connection;

    // Constructor - Initialize database
    public DatabaseHelper() {
        initializeDatabase();
    }

    // Initialize database and create tables
    private void initializeDatabase() {
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            
            // Create phones table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS phones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "brand TEXT NOT NULL, " +
                    "model TEXT NOT NULL, " +
                    "imei TEXT UNIQUE NOT NULL, " +
                    "price REAL NOT NULL, " +
                    "status TEXT DEFAULT 'Available', " +
                    "date_added TEXT NOT NULL" +
                    ")";
            
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSQL);
            stmt.close();
            
            System.out.println("✅ Database initialized successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Database initialization error: " + e.getMessage());
        }
    }

    // ADD PHONE
    public boolean addPhone(Phone phone) {
        String insertSQL = "INSERT INTO phones (brand, model, imei, price, status, date_added) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, phone.getBrand());
            pstmt.setString(2, phone.getModel());
            pstmt.setString(3, phone.getImei());
            pstmt.setDouble(4, phone.getPrice());
            pstmt.setString(5, phone.getStatus());
            pstmt.setString(6, phone.getDateAdded());
            
            pstmt.executeUpdate();
            System.out.println("✅ Phone added successfully!");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error adding phone: " + e.getMessage());
            return false;
        }
    }

    // VIEW ALL PHONES
    public List<Phone> getAllPhones() {
        List<Phone> phones = new ArrayList<>();
        String selectSQL = "SELECT * FROM phones ORDER BY date_added DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                Phone phone = new Phone(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("imei"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("date_added")
                );
                phones.add(phone);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching phones: " + e.getMessage());
        }
        
        return phones;
    }

    // GET PHONE BY ID
    public Phone getPhoneById(int id) {
        String selectSQL = "SELECT * FROM phones WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Phone(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("imei"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("date_added")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching phone: " + e.getMessage());
        }
        
        return null;
    }

    // UPDATE PHONE
    public boolean updatePhone(int id, Phone phone) {
        String updateSQL = "UPDATE phones SET brand = ?, model = ?, imei = ?, price = ?, status = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, phone.getBrand());
            pstmt.setString(2, phone.getModel());
            pstmt.setString(3, phone.getImei());
            pstmt.setDouble(4, phone.getPrice());
            pstmt.setString(5, phone.getStatus());
            pstmt.setInt(6, id);
            
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Phone updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating phone: " + e.getMessage());
        }
        
        return false;
    }

    // DELETE PHONE
    public boolean deletePhone(int id) {
        String deleteSQL = "DELETE FROM phones WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Phone deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting phone: " + e.getMessage());
        }
        
        return false;
    }

    // SEARCH PHONES
    public List<Phone> searchPhones(String keyword) {
        List<Phone> phones = new ArrayList<>();
        String searchSQL = "SELECT * FROM phones WHERE brand LIKE ? OR model LIKE ? OR imei LIKE ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(searchSQL)) {
            String searchTerm = "%" + keyword + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, searchTerm);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Phone phone = new Phone(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("imei"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("date_added")
                );
                phones.add(phone);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error searching phones: " + e.getMessage());
        }
        
        return phones;
    }

    // GET TOTAL PHONES
    public int getTotalPhones() {
        String countSQL = "SELECT COUNT(*) as total FROM phones";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error counting phones: " + e.getMessage());
        }
        
        return 0;
    }

    // GET AVAILABLE PHONES
    public int getAvailablePhones() {
        String countSQL = "SELECT COUNT(*) as total FROM phones WHERE status = 'Available'";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error counting available phones: " + e.getMessage());
        }
        
        return 0;
    }

    // CLOSE DATABASE CONNECTION
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing connection: " + e.getMessage());
        }
    }
}
