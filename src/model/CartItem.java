package model;

public class CartItem {

    private String productId;
    private String productName;
    private double unitPrice;
    private int quantity;

    public CartItem(String productId, String productName, double unitPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity = this.quantity + amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity = this.quantity - amount;
    }

    // Returns price for this line item (unit price * quantity)
    public double getSubtotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return productName + "\t" + quantity + "\t" + unitPrice + "\t" + getSubtotal();
    }
}
