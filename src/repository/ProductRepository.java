package repository;

import java.util.ArrayList;
import java.util.List;

import data.DummyData;
import model.Product;

public class ProductRepository {

    private List<Product> productList;

    public ProductRepository() {
        this.productList = new ArrayList<>(DummyData.getInitialProducts());
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product findById(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equalsIgnoreCase(productId)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> findByNameContains(String keyword) {
        List<Product> matches = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(product);
            }
        }
        return matches;
    }

    public boolean isIdTaken(String productId) {
        return findById(productId) != null;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }
}
