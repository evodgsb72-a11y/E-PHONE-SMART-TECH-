import models.User;
import services.AuthenticationService;
import java.util.Scanner;

public class Main {
    private static AuthenticationService authService = new AuthenticationService();
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

    // Welcome menu before user logs in
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

    // Handles the user login process
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

    // Main Dashboard after successful login
    private static void showDashboard() {
        System.out.println("\n=========================================");
        System.out.println("       📊 DASHBOARD MANAGEMENT           ");
        System.out.println("=========================================");
        System.out.println("1. View My Profile Info");
        System.out.println("2. Add New Phone (Phase 1)");
        System.out.println("3. View Stock / Phones (Phase 1)");
        System.out.println("4. Logout");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                authService.displayUserInfo(currentUser);
                break;
            case "2":
                System.out.println("\n[Feature Coming Soon] - Phone Service setup needed.");
                break;
            case "3":
                System.out.println("\n[Feature Coming Soon] - Stock View setup needed.");
                break;
            case "4":
                authService.logout();
                currentUser = null; // Reset session
                break;
            default:
                System.out.println("❌ Invalid option! Please select a valid menu.");
        }
    }
}
