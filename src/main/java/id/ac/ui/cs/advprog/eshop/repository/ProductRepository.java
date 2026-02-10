package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        try {
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (product.getProductId() == null) {
                product.setProductId(java.util.UUID.randomUUID().toString());
            }
            productData.add(product);
            return product;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage());
        }
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}