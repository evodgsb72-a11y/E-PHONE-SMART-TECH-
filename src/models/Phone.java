package models;

public class Phone {
    private int id;
    private String brand;
    private String model;
    private String imei;
    private double price;
    private String status; // Available, Sold
    private String dateAdded;

    // Constructor
    public Phone(int id, String brand, String model, String imei, double price, String status, String dateAdded) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.imei = imei;
        this.price = price;
        this.status = status;
        this.dateAdded = dateAdded;
    }

    // Constructor without ID (for new phones)
    public Phone(String brand, String model, String imei, double price, String status) {
        this.brand = brand;
        this.model = model;
        this.imei = imei;
        this.price = price;
        this.status = status;
        this.dateAdded = java.time.LocalDate.now().toString();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", imei='" + imei + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
