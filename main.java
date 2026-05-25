import models.User;
import models.Phone;
import services.AuthenticationService;
import services.PhoneService;
import java.util.Scanner;

public class Main {
    private static AuthenticationService authService = new AuthenticationService();
    private static PhoneService phoneService = new PhoneService(); // Tumeweka PhoneService hapa
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("  WELCOME TO E-PHONE SMART TECH APP 📱  ");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                showWelcomeMenu();
            } else {
                showDashboard();
            }
        }
    }

    private static void showWelcomeMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Exit App");
        System.out.print("Choose an option: ");
        
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                System.out.println("Thank you for using E-Phone Smart Tech. Goodbye! 👋");
                System.exit(0);
                break;
            default:
                System.out.println("❌ Invalid option! Please try again.");
        }
    }

    private static void handleLogin() {
        System.out.println("\n--- LOGIN PAGE ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        currentUser = authService.login(username, password);

        if (currentUser != null) {
            System.out.println("\nWelcome back, " + currentUser.getRole() + " " + currentUser.getUsername() + "! 🎉");
        }
    }

    private static void showDashboard() {
        System.out.println("\n=========================================");
        System.out.println("       📊 DASHBOARD MANAGEMENT           ");
        System.out.println("=========================================");
        System.out.println("1. View My Profile Info");
        System.out.println("2. Add New Phone");
        System.out.println("3. View Stock / Phones");
        System.out.println("4. Logout");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                authService.displayUserInfo(currentUser);
                break;
            case "2":
                handleAddPhone(); // Tunaita fomu ya kuongeza simu
                break;
            case "3":
                phoneService.displayAllPhones(); // Tunaonyesha simu zilizopo
                break;
            case "4":
                authService.logout();
                currentUser = null; 
                break;
            default:
                System.out.println("❌ Invalid option! Please select a valid menu.");
        }
    }

    // Fomu inayomwomba user ajaze taarifa za simu mpya
    private static void handleAddPhone() {
        System.out.println("\n--- ADD NEW PHONE TO STOCK ---");
        
        System.out.print("Enter Brand (e.g., Samsung, iPhone): ");
        String brand = scanner.nextLine();
        
        System.out.print("Enter Model (e.g., S23, iPhone 15): ");
        String model = scanner.nextLine();
        
        System.out.print("Enter IMEI Number: ");
        String imei = scanner.nextLine();
        
        System.out.print("Enter Price (TZS): ");
        double price = 0.0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid price format! Setting default to 0.0");
        }

        // Simu mpya inakuwa "Available" automatically wakati wa kuiingiza stock
        Phone newPhone = new Phone(brand, model, imei, price, "Available");
        
        // Tunaisave kwenye PhoneService
        phoneService.addPhone(newPhone);
    }
}
