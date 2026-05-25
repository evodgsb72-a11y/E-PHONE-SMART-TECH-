package models;

public class Phone {
    private int id;
    private String brand;
    private String model;
    private String imei;
    private double price;
    private String status; // Mfano: "Available" au "Sold"

    // Constructor ya kuunda simu mpya (bila ID kwa sababu ID itakuwa ya kuji-add yenyewe mbeleni)
    public Phone(String brand, String model, String imei, double price, String status) {
        this.brand = brand;
        this.model = model;
        this.imei = imei;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
