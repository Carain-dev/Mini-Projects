package repository;

import java.util.ArrayList;
import java.util.List;

import data.DummyData;
import model.Customer;

public class CustomerRepository {

    private List<Customer> customerList;

    public CustomerRepository() {
        this.customerList = new ArrayList<>(DummyData.getInitialCustomers());
    }

    public List<Customer> getAllCustomers() {
        return customerList;
    }

    // Searches for a customer by email, used during login
    public Customer findByEmail(String email) {
        for (Customer customer : customerList) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findById(String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public boolean isEmailTaken(String email) {
        return findByEmail(email) != null;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }
}
