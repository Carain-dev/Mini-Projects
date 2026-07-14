package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Bill;
import model.CartItem;
import model.Customer;
import model.Product;
import repository.BillRepository;
import repository.ProductRepository;
import utility.IDGenerator;

public class BillingService {

    private static final double CASHBACK_THRESHOLD = 5000.0;
    private static final double HIGH_SPEND_CASHBACK = 100.0;
    private static final double LOYALTY_REDEMPTION_CASHBACK = 100.0;
    private static final int LOYALTY_POINTS_REQUIRED = 50;

    private ProductRepository productRepository;
    private BillRepository billRepository;

    public BillingService(ProductRepository productRepository, BillRepository billRepository) {
        this.productRepository = productRepository;
        this.billRepository = billRepository;
    }

    public void checkout(Customer customer, List<CartItem> cartItems) {
        System.out.println("\n--- Checkout ---");

        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        double total = 0;
        System.out.println("Product\tQuantity\tUnit Price\tSubtotal");
        for (CartItem item : cartItems) {
            System.out.println(item);
            total += item.getSubtotal();
        }
        System.out.println("Total: " + total);

        if (customer.getWalletBalance() < total) {
            System.out.println("Insufficient Credit");
            return;
        }

        double walletBefore = customer.getWalletBalance();
        customer.deductFromWallet(total);

        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId());
            if (product != null) {
                product.reduceQuantity(item.getQuantity());
            }
        }

        double cashbackEarned = 0.0;
        int loyaltyEarned = 0;

        if (total >= CASHBACK_THRESHOLD) {
            cashbackEarned += HIGH_SPEND_CASHBACK;
            customer.addToWallet(HIGH_SPEND_CASHBACK);
        } else {
            loyaltyEarned = (int) (total / 100);
            customer.addLoyaltyPoints(loyaltyEarned);

            while (customer.getLoyaltyPoints() >= LOYALTY_POINTS_REQUIRED) {
                customer.deductLoyaltyPoints(LOYALTY_POINTS_REQUIRED);
                customer.addToWallet(LOYALTY_REDEMPTION_CASHBACK);
                cashbackEarned += LOYALTY_REDEMPTION_CASHBACK;
                System.out.println("Congratulations!");
                System.out.println("You earned Rs.100 loyalty reward.");
            }
        }

        double walletAfter = customer.getWalletBalance();

        String billNumber = IDGenerator.generateBillId();
        Bill bill = new Bill(billNumber, customer.getCustomerId(), customer.getName(), LocalDateTime.now());
        bill.setPurchasedItems(new ArrayList<>(cartItems));
        bill.setTotalAmount(total);
        bill.setWalletBefore(walletBefore);
        bill.setWalletAfter(walletAfter);
        bill.setCashbackEarned(cashbackEarned);
        bill.setLoyaltyEarned(loyaltyEarned);

        billRepository.addBill(bill);
        cartItems.clear();

        printInvoice(bill);
    }

    public void printInvoice(Bill bill) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("\n========== INVOICE ==========");
        System.out.println("Bill Number : " + bill.getBillNumber());
        System.out.println("Customer    : " + bill.getCustomerName());
        System.out.println("Date        : " + bill.getBillDate().format(formatter));
        System.out.println("------------------------------");
        System.out.println("Product\tQuantity\tUnit Price\tSubtotal");
        for (CartItem item : bill.getPurchasedItems()) {
            System.out.println(item);
        }
        System.out.println("------------------------------");
        System.out.println("Total          : " + bill.getTotalAmount());
        System.out.println("Wallet Before  : " + bill.getWalletBefore());
        System.out.println("Wallet After   : " + bill.getWalletAfter());
        System.out.println("Cashback       : " + bill.getCashbackEarned());
        System.out.println("Loyalty Earned : " + bill.getLoyaltyEarned());
        System.out.println("==============================\n");
    }
}
