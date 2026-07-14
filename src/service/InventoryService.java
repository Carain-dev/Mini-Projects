package service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import model.Admin;
import model.Customer;
import model.Product;
import repository.AdminRepository;
import repository.CustomerRepository;
import repository.ProductRepository;
import utility.IDGenerator;
import utility.InputValidator;

public class InventoryService {

    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private AdminRepository adminRepository;
    private AdminActivityTracker activityTracker;

    public InventoryService(ProductRepository productRepository, CustomerRepository customerRepository,
                             AdminRepository adminRepository, AdminActivityTracker activityTracker) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
        this.activityTracker = activityTracker;
    }

    public void addProduct(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n--- Add Product ---");

        String productId = IDGenerator.generateProductId();
        while (productRepository.isIdTaken(productId)) {
            productId = IDGenerator.generateProductId();
        }
        System.out.println("Generated Product ID: " + productId);

        String name = InputValidator.readNonEmptyString(scanner, "Product Name: ");
        String category = InputValidator.readNonEmptyString(scanner, "Category: ");
        double price = InputValidator.readPositiveDouble(scanner, "Price: ");
        int quantity = InputValidator.readNonNegativeInt(scanner, "Quantity: ");

        Product product = new Product(productId, name, category, price, quantity);
        productRepository.addProduct(product);
        activityTracker.recordProductAdded(currentAdmin.getAdminId());

        System.out.println("Product added successfully.");
    }

    public void modifyProduct(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n--- Modify Product ---");
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        Product product = productRepository.findById(productId);

        if (product == null) {
            System.out.println("Not Found");
            return;
        }

        System.out.println("Leave blank to keep current value.");

        System.out.print("Name [" + product.getProductName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            product.setProductName(name);
        }

        System.out.print("Category [" + product.getCategory() + "]: ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            product.setCategory(category);
        }

        System.out.print("Price [" + product.getPrice() + "]: ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            try {
                double price = Double.parseDouble(priceInput);
                if (price > 0) {
                    product.setPrice(price);
                } else {
                    System.out.println("Price must be greater than 0. Keeping old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Keeping old value.");
            }
        }

        System.out.print("Quantity [" + product.getQuantity() + "]: ");
        String quantityInput = scanner.nextLine().trim();
        if (!quantityInput.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityInput);
                if (quantity >= 0) {
                    product.setQuantity(quantity);
                } else {
                    System.out.println("Quantity cannot be negative. Keeping old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Keeping old value.");
            }
        }

        activityTracker.recordProductModified(currentAdmin.getAdminId());
        System.out.println("Product updated successfully.");
    }

    public void deleteProduct(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n--- Delete Product ---");
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        Product product = productRepository.findById(productId);

        if (product == null) {
            System.out.println("Not Found");
            return;
        }

        System.out.println(product);
        boolean confirmed = InputValidator.readYesNo(scanner, "Are you sure you want to delete this product? (Y/N): ");
        if (confirmed) {
            productRepository.removeProduct(product);
            activityTracker.recordProductDeleted(currentAdmin.getAdminId());
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public void searchProduct(Scanner scanner) {
        System.out.println("\n--- Search Product ---");
        System.out.println("1 Search by Product ID");
        System.out.println("2 Search by Product Name");
        int choice = InputValidator.readInt(scanner, "Enter Choice: ");

        if (choice == 1) {
            String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
            Product product = productRepository.findById(productId);
            if (product == null) {
                System.out.println("Not Found");
            } else {
                printProductTableHeader();
                System.out.println(product);
            }
        } else if (choice == 2) {
            String name = InputValidator.readNonEmptyString(scanner, "Enter Product Name: ");
            List<Product> matches = productRepository.findByNameContains(name);
            if (matches.isEmpty()) {
                System.out.println("Not Found");
            } else {
                printProductTableHeader();
                for (Product product : matches) {
                    System.out.println(product);
                }
            }
        } else {
            System.out.println("Invalid Choice.");
        }
    }

    public void viewProducts(Scanner scanner) {
        System.out.println("\n--- View Products ---");
        System.out.println("1 Sort by Name");
        System.out.println("2 Sort by Price Low to High");
        System.out.println("3 Sort by Price High to Low");
        int choice = InputValidator.readInt(scanner, "Enter Choice: ");

        List<Product> products = productRepository.getAllProducts();

        if (choice == 1) {
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product firstProduct, Product secondProduct) {
                    return firstProduct.getProductName().compareToIgnoreCase(secondProduct.getProductName());
                }
            });
        } else if (choice == 2) {
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product firstProduct, Product secondProduct) {
                    return Double.compare(firstProduct.getPrice(), secondProduct.getPrice());
                }
            });
        } else if (choice == 3) {
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product firstProduct, Product secondProduct) {
                    return Double.compare(secondProduct.getPrice(), firstProduct.getPrice());
                }
            });
        } else {
            System.out.println("Invalid Choice. Showing unsorted list.");
        }

        printProductTableHeader();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void restockProduct(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n--- Restock Product ---");
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        Product product = productRepository.findById(productId);

        if (product == null) {
            System.out.println("Not Found");
            return;
        }

        int addQuantity = InputValidator.readInt(scanner, "Enter Quantity to Add: ");
        if (addQuantity <= 0) {
            System.out.println("Quantity to add must be greater than 0.");
            return;
        }

        product.increaseQuantity(addQuantity);
        activityTracker.recordProductModified(currentAdmin.getAdminId());
        System.out.println("Stock updated. New Quantity: " + product.getQuantity());
    }

    public void createCustomer(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n--- Create Customer ---");

        String name = InputValidator.readNonEmptyString(scanner, "Customer Name: ");
        String email = readUniqueCustomerEmail(scanner);
        String password = InputValidator.readNonEmptyString(scanner, "Password: ");

        String customerId = IDGenerator.generateCustomerId();
        while (customerRepository.findById(customerId) != null) {
            customerId = IDGenerator.generateCustomerId();
        }

        Customer customer = new Customer(customerId, name, email, password, 1000.0, 0);
        customerRepository.addCustomer(customer);
        activityTracker.recordCustomerAdded(currentAdmin.getAdminId());

        System.out.println("Customer created successfully. Customer ID: " + customerId);
        System.out.println("Starting Wallet Balance: 1000.0");
    }

    public void createAdmin(Scanner scanner, Admin currentAdmin) {
        System.out.println("\n--- Create Admin ---");

        String name = InputValidator.readNonEmptyString(scanner, "Admin Name: ");
        String email = readUniqueAdminEmail(scanner);
        String password = InputValidator.readNonEmptyString(scanner, "Password: ");

        String adminId = IDGenerator.generateAdminId();
        while (adminRepository.findById(adminId) != null) {
            adminId = IDGenerator.generateAdminId();
        }

        Admin newAdmin = new Admin(adminId, name, email, password);
        adminRepository.addAdmin(newAdmin);
        activityTracker.recordAdminAdded(currentAdmin.getAdminId());

        System.out.println("Admin created successfully. Admin ID: " + adminId);
    }

    private String readUniqueCustomerEmail(Scanner scanner) {
        while (true) {
            String email = InputValidator.readNonEmptyString(scanner, "Email: ");
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Invalid email format.");
            } else if (customerRepository.isEmailTaken(email)) {
                System.out.println("Email already exists.");
            } else {
                return email;
            }
        }
    }

    private String readUniqueAdminEmail(Scanner scanner) {
        while (true) {
            String email = InputValidator.readNonEmptyString(scanner, "Email: ");
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Invalid email format.");
            } else if (adminRepository.isEmailTaken(email)) {
                System.out.println("Email already exists.");
            } else {
                return email;
            }
        }
    }

    private void printProductTableHeader() {
        System.out.println("ID\tName\tCategory\tPrice\tQuantity");
    }
}
