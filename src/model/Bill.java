package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bill {

    private String billNumber;
    private String customerId;
    private String customerName;
    private LocalDateTime billDate;
    private List<CartItem> purchasedItems;
    private double totalAmount;
    private double walletBefore;
    private double walletAfter;
    private double cashbackEarned;
    private int loyaltyEarned;

    public Bill(String billNumber, String customerId, String customerName, LocalDateTime billDate) {
        this.billNumber = billNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.billDate = billDate;
        this.purchasedItems = new ArrayList<>();
        this.totalAmount = 0.0;
        this.walletBefore = 0.0;
        this.walletAfter = 0.0;
        this.cashbackEarned = 0.0;
        this.loyaltyEarned = 0;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public List<CartItem> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<CartItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getWalletBefore() {
        return walletBefore;
    }

    public void setWalletBefore(double walletBefore) {
        this.walletBefore = walletBefore;
    }

    public double getWalletAfter() {
        return walletAfter;
    }

    public void setWalletAfter(double walletAfter) {
        this.walletAfter = walletAfter;
    }

    public double getCashbackEarned() {
        return cashbackEarned;
    }

    public void setCashbackEarned(double cashbackEarned) {
        this.cashbackEarned = cashbackEarned;
    }

    public int getLoyaltyEarned() {
        return loyaltyEarned;
    }

    public void setLoyaltyEarned(int loyaltyEarned) {
        this.loyaltyEarned = loyaltyEarned;
    }

    @Override
    public String toString() {
        return billNumber + "\t" + billDate + "\t" + totalAmount;
    }
}
