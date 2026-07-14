package service;

import java.util.Scanner;

import model.Admin;
import model.Customer;
import repository.AdminRepository;
import repository.CustomerRepository;

public class AuthenticationService {

    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private AdminRepository adminRepository;
    private CustomerRepository customerRepository;

    public AuthenticationService(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    // Tries to log the user in using email and password.
    // Allows a maximum of 3 attempts.
    // Returns the matched Admin or Customer object on success, or null if all attempts fail.
    public Object login(Scanner scanner) {
        int attemptsRemaining = MAX_LOGIN_ATTEMPTS;

        while (attemptsRemaining > 0) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();

            Admin matchedAdmin = adminRepository.findByEmail(email);
            if (matchedAdmin != null && matchedAdmin.getPassword().equals(password)) {
                return matchedAdmin;
            }

            Customer matchedCustomer = customerRepository.findByEmail(email);
            if (matchedCustomer != null && matchedCustomer.getPassword().equals(password)) {
                return matchedCustomer;
            }

            attemptsRemaining--;
            System.out.println("Invalid Email or Password");

            if (attemptsRemaining > 0) {
                System.out.println("Attempts remaining: " + attemptsRemaining);
            }
        }

        System.out.println("Maximum login attempts reached.");
        return null;
    }
}
