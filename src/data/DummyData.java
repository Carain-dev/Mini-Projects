package data;

import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Customer;
import model.Product;

// This class provides the initial dummy data for the application.
// In a real project this data would come from a database, but here
// we are just hardcoding it so the application has something to work with.
public class DummyData {

    // Creates 5 admins with simple login credentials
    public static List<Admin> getInitialAdmins() {
        List<Admin> adminList = new ArrayList<>();

        adminList.add(new Admin("A101", "Rajesh Kumar", "rajesh.admin@market.com", "admin123"));
        adminList.add(new Admin("A102", "Priya Sharma", "priya.admin@market.com", "admin123"));
        adminList.add(new Admin("A103", "Suresh Babu", "suresh.admin@market.com", "admin123"));
        adminList.add(new Admin("A104", "Anita Reddy", "anita.admin@market.com", "admin123"));
        adminList.add(new Admin("A105", "Vikram Singh", "vikram.admin@market.com", "admin123"));

        return adminList;
    }

    // Creates 10 customers, each starting with wallet balance 1000 and 0 loyalty points
    public static List<Customer> getInitialCustomers() {
        List<Customer> customerList = new ArrayList<>();

        customerList.add(new Customer("C101", "Arun Prakash", "arun@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C102", "Divya Menon", "divya@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C103", "Karthik Raja", "karthik@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C104", "Meena Iyer", "meena@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C105", "Naveen Kumar", "naveen@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C106", "Pooja Verma", "pooja@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C107", "Ravi Shankar", "ravi@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C108", "Sneha Pillai", "sneha@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C109", "Tarun Joseph", "tarun@example.com", "cust123", 1000.0, 0));
        customerList.add(new Customer("C110", "Uma Devi", "uma@example.com", "cust123", 1000.0, 0));

        return customerList;
    }

    // Creates 25 products spread across 7 categories with different prices and quantities
    public static List<Product> getInitialProducts() {
        List<Product> productList = new ArrayList<>();

        // Groceries
        productList.add(new Product("P101", "Basmati Rice 5kg", "Groceries", 480.0, 40));
        productList.add(new Product("P102", "Toor Dal 1kg", "Groceries", 140.0, 60));
        productList.add(new Product("P103", "Sunflower Oil 1L", "Groceries", 165.0, 50));
        productList.add(new Product("P104", "Wheat Atta 5kg", "Groceries", 260.0, 35));
        productList.add(new Product("P105", "Sugar 1kg", "Groceries", 48.0, 8));

        // Beverages
        productList.add(new Product("P106", "Tata Tea Gold 500g", "Beverages", 250.0, 30));
        productList.add(new Product("P107", "Nescafe Coffee 200g", "Beverages", 320.0, 25));
        productList.add(new Product("P108", "Real Orange Juice 1L", "Beverages", 120.0, 45));
        productList.add(new Product("P109", "Bisleri Water 1L", "Beverages", 20.0, 100));
        productList.add(new Product("P110", "Coca-Cola 750ml", "Beverages", 45.0, 6));

        // Snacks
        productList.add(new Product("P111", "Lays Classic Salted", "Snacks", 20.0, 80));
        productList.add(new Product("P112", "Haldiram Bhujia 200g", "Snacks", 55.0, 40));
        productList.add(new Product("P113", "Britannia Good Day", "Snacks", 30.0, 55));
        productList.add(new Product("P114", "Parle-G Biscuit Pack", "Snacks", 10.0, 120));
        productList.add(new Product("P115", "Kurkure Masala Munch", "Snacks", 20.0, 5));

        // Personal Care
        productList.add(new Product("P116", "Colgate Toothpaste 150g", "Personal Care", 95.0, 40));
        productList.add(new Product("P117", "Dove Soap 100g", "Personal Care", 55.0, 60));
        productList.add(new Product("P118", "Head and Shoulders 340ml", "Personal Care", 285.0, 20));
        productList.add(new Product("P119", "Nivea Body Lotion 200ml", "Personal Care", 210.0, 15));

        // Cleaning
        productList.add(new Product("P120", "Surf Excel 1kg", "Cleaning", 145.0, 30));
        productList.add(new Product("P121", "Vim Dishwash Bar", "Cleaning", 25.0, 70));
        productList.add(new Product("P122", "Harpic Toilet Cleaner 500ml", "Cleaning", 99.0, 25));

        // Stationery
        productList.add(new Product("P123", "Classmate Notebook 200pg", "Stationery", 60.0, 50));
        productList.add(new Product("P124", "Reynolds Ball Pen Pack", "Stationery", 40.0, 90));

        // Electronics
        productList.add(new Product("P125", "Philips LED Bulb 9W", "Electronics", 120.0, 45));

        return productList;
    }
}
