import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Admin;
import model.CartItem;
import model.Customer;
import repository.AdminRepository;
import repository.BillRepository;
import repository.CustomerRepository;
import repository.ProductRepository;
import service.AdminActivityTracker;
import service.AuthenticationService;
import service.BillingService;
import service.CartService;
import service.InventoryService;
import service.PurchaseHistoryService;
import service.ReportService;
import utility.MenuPrinter;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Repositories - hold all in-memory data for the application
    private static AdminRepository adminRepository = new AdminRepository();
    private static CustomerRepository customerRepository = new CustomerRepository();
    private static ProductRepository productRepository = new ProductRepository();
    private static BillRepository billRepository = new BillRepository();

    // Services - contain the business logic for each module
    private static AdminActivityTracker activityTracker = new AdminActivityTracker();
    private static AuthenticationService authenticationService =
            new AuthenticationService(adminRepository, customerRepository);
    private static InventoryService inventoryService =
            new InventoryService(productRepository, customerRepository, adminRepository, activityTracker);
    private static CartService cartService = new CartService(productRepository);
    private static BillingService billingService = new BillingService(productRepository, billRepository);
    private static PurchaseHistoryService purchaseHistoryService =
            new PurchaseHistoryService(billRepository, billingService);
    private static ReportService reportService =
            new ReportService(productRepository, customerRepository, billRepository, adminRepository, activityTracker);

    public static void main(String[] args) {
        boolean applicationRunning = true;

        while (applicationRunning) {
            showWelcomeScreen();
            int choice = readMenuChoice("Enter Choice : ");

            if (choice == 1) {
                handleLogin();
            } else if (choice == 2) {
                applicationRunning = !confirmExit();
            } else {
                System.out.println("Invalid Choice. Please try again.");
            }
        }

        System.out.println("Thank you for using Supermarket Billing System.");
        scanner.close();
    }

    private static void showWelcomeScreen() {
        System.out.println();
        MenuPrinter.printHeader("SUPERMARKET BILLING SYSTEM");
        System.out.println();
        System.out.println("1 Login");
        System.out.println("2 Exit");
    }

    private static int readMenuChoice(String prompt) {
        System.out.print("\n" + prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void handleLogin() {
        Object loggedInUser = authenticationService.login(scanner);

        if (loggedInUser == null) {
            System.out.println("Returning to main menu.\n");
            return;
        }

        if (loggedInUser instanceof Admin) {
            Admin loggedInAdmin = (Admin) loggedInUser;
            System.out.println("\nWelcome");
            System.out.println(loggedInAdmin.getName());
            System.out.println("Admin");
            runAdminSession(loggedInAdmin);
        } else if (loggedInUser instanceof Customer) {
            Customer loggedInCustomer = (Customer) loggedInUser;
            System.out.println("\nWelcome");
            System.out.println(loggedInCustomer.getName());
            System.out.println("Customer");
            runCustomerSession(loggedInCustomer);
        }
    }

    private static void runAdminSession(Admin admin) {
        boolean inSession = true;

        while (inSession) {
            System.out.println("\n----- ADMIN MENU -----");
            System.out.println("1 Add Product");
            System.out.println("2 Modify Product");
            System.out.println("3 Delete Product");
            System.out.println("4 Search Product");
            System.out.println("5 View Products");
            System.out.println("6 Restock Product");
            System.out.println("7 Create Customer");
            System.out.println("8 Create Admin");
            System.out.println("9 Reports");
            System.out.println("10 Logout");
            int choice = readMenuChoice("Enter Choice : ");

            switch (choice) {
                case 1:
                    inventoryService.addProduct(scanner, admin);
                    break;
                case 2:
                    inventoryService.modifyProduct(scanner, admin);
                    break;
                case 3:
                    inventoryService.deleteProduct(scanner, admin);
                    break;
                case 4:
                    inventoryService.searchProduct(scanner);
                    break;
                case 5:
                    inventoryService.viewProducts(scanner);
                    break;
                case 6:
                    inventoryService.restockProduct(scanner, admin);
                    break;
                case 7:
                    inventoryService.createCustomer(scanner, admin);
                    break;
                case 8:
                    inventoryService.createAdmin(scanner, admin);
                    break;
                case 9:
                    runReportsMenu();
                    break;
                case 10:
                    inSession = false;
                    System.out.println("\nLogging out...\n");
                    break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }
    }

    private static void runReportsMenu() {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n----- REPORTS -----");
            System.out.println("1 Low Stock Report");
            System.out.println("2 Products Never Purchased");
            System.out.println("3 Top Spending Customers");
            System.out.println("4 Admin Activity Report");
            System.out.println("5 Back");
            int choice = readMenuChoice("Enter Choice : ");

            switch (choice) {
                case 1:
                    reportService.lowStockReport();
                    break;
                case 2:
                    reportService.neverPurchasedReport();
                    break;
                case 3:
                    reportService.topSpendingCustomersReport();
                    break;
                case 4:
                    reportService.adminActivityReport();
                    break;
                case 5:
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }
    }

    private static void runCustomerSession(Customer customer) {
        List<CartItem> cartItems = new ArrayList<>();
        boolean inSession = true;

        while (inSession) {
            System.out.println("\n----- CUSTOMER MENU -----");
            System.out.println("1 View Products");
            System.out.println("2 Search Product");
            System.out.println("3 Add Product to Cart");
            System.out.println("4 View Cart");
            System.out.println("5 Checkout");
            System.out.println("6 Purchase History");
            System.out.println("7 Logout");
            int choice = readMenuChoice("Enter Choice : ");

            switch (choice) {
                case 1:
                    inventoryService.viewProducts(scanner);
                    break;
                case 2:
                    inventoryService.searchProduct(scanner);
                    break;
                case 3:
                    cartService.addToCart(scanner, cartItems);
                    break;
                case 4:
                    cartService.manageCart(scanner, cartItems);
                    break;
                case 5:
                    billingService.checkout(customer, cartItems);
                    break;
                case 6:
                    purchaseHistoryService.viewHistory(scanner, customer);
                    break;
                case 7:
                    inSession = false;
                    System.out.println("\nLogging out...\n");
                    break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }
    }

    private static boolean confirmExit() {
        System.out.print("Are you sure you want to exit? (Y/N): ");
        String confirmation = scanner.nextLine().trim();
        return confirmation.equalsIgnoreCase("Y");
    }
}
