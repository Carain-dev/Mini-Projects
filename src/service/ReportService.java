package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Admin;
import model.Bill;
import model.CartItem;
import model.Customer;
import model.Product;
import repository.AdminRepository;
import repository.BillRepository;
import repository.CustomerRepository;
import repository.ProductRepository;

public class ReportService {

    private static final int LOW_STOCK_THRESHOLD = 10;

    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private BillRepository billRepository;
    private AdminRepository adminRepository;
    private AdminActivityTracker activityTracker;

    public ReportService(ProductRepository productRepository, CustomerRepository customerRepository,
                          BillRepository billRepository, AdminRepository adminRepository,
                          AdminActivityTracker activityTracker) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.billRepository = billRepository;
        this.adminRepository = adminRepository;
        this.activityTracker = activityTracker;
    }

    // Report 1: Products with quantity less than 10
    public void lowStockReport() {
        System.out.println("\n--- Low Stock Report (Quantity < 10) ---");
        boolean found = false;

        for (Product product : productRepository.getAllProducts()) {
            if (product.getQuantity() < LOW_STOCK_THRESHOLD) {
                System.out.println(product);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products are low in stock.");
        }
    }

    // Report 2: Products that have never appeared in any bill
    public void neverPurchasedReport() {
        System.out.println("\n--- Products Never Purchased ---");

        Set<String> purchasedProductIds = new HashSet<>();
        for (Bill bill : billRepository.getAllBills()) {
            for (CartItem item : bill.getPurchasedItems()) {
                purchasedProductIds.add(item.getProductId());
            }
        }

        boolean found = false;
        for (Product product : productRepository.getAllProducts()) {
            if (!purchasedProductIds.contains(product.getProductId())) {
                System.out.println(product);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Every product has been purchased at least once.");
        }
    }

    // Report 3: Customers ranked by total amount spent
    public void topSpendingCustomersReport() {
        System.out.println("\n--- Top Spending Customers ---");

        List<Customer> sortedCustomers = new ArrayList<>(customerRepository.getAllCustomers());

        // Simple bubble sort by total spend, descending (kept beginner-friendly, no lambdas)
        for (int i = 0; i < sortedCustomers.size() - 1; i++) {
            for (int j = 0; j < sortedCustomers.size() - 1 - i; j++) {
                double spendCurrent = getTotalSpend(sortedCustomers.get(j).getCustomerId());
                double spendNext = getTotalSpend(sortedCustomers.get(j + 1).getCustomerId());
                if (spendCurrent < spendNext) {
                    Customer temp = sortedCustomers.get(j);
                    sortedCustomers.set(j, sortedCustomers.get(j + 1));
                    sortedCustomers.set(j + 1, temp);
                }
            }
        }

        System.out.println("Name\tTotal Bills\tTotal Amount\tWallet Balance\tLoyalty Points");
        for (Customer customer : sortedCustomers) {
            int billCount = billRepository.getBillsByCustomerId(customer.getCustomerId()).size();
            double totalSpend = getTotalSpend(customer.getCustomerId());
            System.out.println(customer.getName() + "\t" + billCount + "\t" + totalSpend
                    + "\t" + customer.getWalletBalance() + "\t" + customer.getLoyaltyPoints());
        }
    }

    private double getTotalSpend(String customerId) {
        double total = 0;
        for (Bill bill : billRepository.getBillsByCustomerId(customerId)) {
            total += bill.getTotalAmount();
        }
        return total;
    }

    // Report 4: Admin Activity - reinterpreted since admins do not make purchases
    public void adminActivityReport() {
        System.out.println("\n--- Admin Activity Report ---");

        List<Admin> admins = adminRepository.getAllAdmins();
        String topAdminId = null;
        int topOperations = -1;

        System.out.println("Admin\tAdded\tModified\tDeleted\tCustomersAdded\tAdminsAdded\tTotal");
        for (Admin admin : admins) {
            int added = activityTracker.getProductsAdded(admin.getAdminId());
            int modified = activityTracker.getProductsModified(admin.getAdminId());
            int deleted = activityTracker.getProductsDeleted(admin.getAdminId());
            int customersAdded = activityTracker.getCustomersAdded(admin.getAdminId());
            int adminsAdded = activityTracker.getAdminsAdded(admin.getAdminId());
            int total = activityTracker.getTotalOperations(admin.getAdminId());

            System.out.println(admin.getName() + "\t" + added + "\t" + modified + "\t" + deleted
                    + "\t" + customersAdded + "\t" + adminsAdded + "\t" + total);

            if (total > topOperations) {
                topOperations = total;
                topAdminId = admin.getAdminId();
            }
        }

        if (topAdminId != null && topOperations > 0) {
            Admin topAdmin = adminRepository.findById(topAdminId);
            System.out.println("\nMost Active Admin: " + topAdmin.getName() + " (" + topOperations + " operations)");
        } else {
            System.out.println("\nNo admin activity recorded yet.");
        }
    }
}
