package service;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import model.Bill;
import model.Customer;
import repository.BillRepository;
import utility.InputValidator;

public class PurchaseHistoryService {

    private BillRepository billRepository;
    private BillingService billingService;

    public PurchaseHistoryService(BillRepository billRepository, BillingService billingService) {
        this.billRepository = billRepository;
        this.billingService = billingService;
    }

    public void viewHistory(Scanner scanner, Customer customer) {
        System.out.println("\n--- Purchase History ---");
        List<Bill> bills = billRepository.getBillsByCustomerId(customer.getCustomerId());

        if (bills.isEmpty()) {
            System.out.println("No Purchase History Found.");
            return;
        }

        System.out.println("1 Latest First");
        System.out.println("2 Oldest First");
        int choice = InputValidator.readInt(scanner, "Enter Choice: ");

        if (choice == 2) {
            Collections.sort(bills, new Comparator<Bill>() {
                public int compare(Bill firstBill, Bill secondBill) {
                    return firstBill.getBillDate().compareTo(secondBill.getBillDate());
                }
            });
        } else {
            Collections.sort(bills, new Comparator<Bill>() {
                public int compare(Bill firstBill, Bill secondBill) {
                    return secondBill.getBillDate().compareTo(firstBill.getBillDate());
                }
            });
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("\nBill Number\tDate\t\tTotal");
        for (Bill bill : bills) {
            System.out.println(bill.getBillNumber() + "\t" + bill.getBillDate().format(formatter)
                    + "\t" + bill.getTotalAmount());
        }

        boolean viewMore = InputValidator.readYesNo(scanner, "\nView a complete bill? (Y/N): ");
        if (viewMore) {
            String billNumber = InputValidator.readNonEmptyString(scanner, "Enter Bill Number: ");
            Bill selectedBill = billRepository.findByBillNumber(billNumber);
            if (selectedBill == null || !selectedBill.getCustomerId().equalsIgnoreCase(customer.getCustomerId())) {
                System.out.println("Not Found");
            } else {
                billingService.printInvoice(selectedBill);
            }
        }
    }
}
