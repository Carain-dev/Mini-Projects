package model;

public class Product {

    private String productId;
    private String productName;
    private String category;
    private double price;
    private int quantity;

    public Product(String productId, String productName, String category, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Reduces stock after a successful purchase
    public void reduceQuantity(int amount) {
        this.quantity = this.quantity - amount;
    }

    // Adds stock when admin restocks a product
    public void increaseQuantity(int amount) {
        this.quantity = this.quantity + amount;
    }

    @Override
    public String toString() {
        return productId + "\t" + productName + "\t" + category + "\t" + price + "\t" + quantity;
    }
}
