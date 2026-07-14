package service;

import java.util.List;
import java.util.Scanner;

import model.CartItem;
import model.Product;
import repository.ProductRepository;
import utility.InputValidator;

public class CartService {

    private ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addToCart(Scanner scanner, List<CartItem> cartItems) {
        System.out.println("\n--- Add Product to Cart ---");
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        Product product = productRepository.findById(productId);

        if (product == null) {
            System.out.println("Not Found");
            return;
        }

        int quantity = InputValidator.readInt(scanner, "Enter Quantity: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        CartItem existingItem = findCartItem(cartItems, productId);
        int alreadyInCart = (existingItem == null) ? 0 : existingItem.getQuantity();

        if (quantity + alreadyInCart > product.getQuantity()) {
            System.out.println("Only " + product.getQuantity() + " units available in stock.");
            return;
        }

        if (existingItem != null) {
            existingItem.increaseQuantity(quantity);
        } else {
            cartItems.add(new CartItem(product.getProductId(), product.getProductName(),
                    product.getPrice(), quantity));
        }

        System.out.println("Added to cart.");
    }

    public void viewCart(List<CartItem> cartItems) {
        System.out.println("\n--- Your Cart ---");
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Product\tQuantity\tUnit Price\tSubtotal");
        double total = 0;
        for (CartItem item : cartItems) {
            System.out.println(item);
            total += item.getSubtotal();
        }
        System.out.println("Cart Total: " + total);
    }

    // Interactive cart screen: view, increase, decrease, remove, clear, or return to shopping
    public void manageCart(Scanner scanner, List<CartItem> cartItems) {
        viewCart(cartItems);
        boolean managing = true;

        while (managing) {
            System.out.println("\n--- Manage Cart ---");
            System.out.println("1 View Cart");
            System.out.println("2 Increase Quantity");
            System.out.println("3 Decrease Quantity");
            System.out.println("4 Remove Product");
            System.out.println("5 Clear Cart");
            System.out.println("6 Return to Shopping");
            int choice = InputValidator.readInt(scanner, "Enter Choice: ");

            switch (choice) {
                case 1:
                    viewCart(cartItems);
                    break;
                case 2:
                    increaseQuantity(scanner, cartItems);
                    break;
                case 3:
                    decreaseQuantity(scanner, cartItems);
                    break;
                case 4:
                    removeProduct(scanner, cartItems);
                    break;
                case 5:
                    clearCart(cartItems);
                    break;
                case 6:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    private void increaseQuantity(Scanner scanner, List<CartItem> cartItems) {
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        CartItem item = findCartItem(cartItems, productId);

        if (item == null) {
            System.out.println("Not Found in cart.");
            return;
        }

        Product product = productRepository.findById(productId);
        int addQuantity = InputValidator.readInt(scanner, "Enter Quantity to Add: ");

        if (addQuantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (item.getQuantity() + addQuantity > product.getQuantity()) {
            System.out.println("Only " + product.getQuantity() + " units available in stock.");
            return;
        }

        item.increaseQuantity(addQuantity);
        System.out.println("Quantity updated.");
    }

    private void decreaseQuantity(Scanner scanner, List<CartItem> cartItems) {
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        CartItem item = findCartItem(cartItems, productId);

        if (item == null) {
            System.out.println("Not Found in cart.");
            return;
        }

        int removeQuantity = InputValidator.readInt(scanner, "Enter Quantity to Remove: ");

        if (removeQuantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (removeQuantity >= item.getQuantity()) {
            cartItems.remove(item);
            System.out.println("Product removed from cart.");
        } else {
            item.decreaseQuantity(removeQuantity);
            System.out.println("Quantity updated.");
        }
    }

    private void removeProduct(Scanner scanner, List<CartItem> cartItems) {
        String productId = InputValidator.readNonEmptyString(scanner, "Enter Product ID: ");
        CartItem item = findCartItem(cartItems, productId);

        if (item == null) {
            System.out.println("Not Found in cart.");
            return;
        }

        cartItems.remove(item);
        System.out.println("Product removed from cart.");
    }

    private void clearCart(List<CartItem> cartItems) {
        cartItems.clear();
        System.out.println("Cart cleared.");
    }

    private CartItem findCartItem(List<CartItem> cartItems, String productId) {
        for (CartItem item : cartItems) {
            if (item.getProductId().equalsIgnoreCase(productId)) {
                return item;
            }
        }
        return null;
    }
}
