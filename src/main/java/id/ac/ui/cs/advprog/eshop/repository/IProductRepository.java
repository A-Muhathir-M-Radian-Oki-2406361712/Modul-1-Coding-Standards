package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;

public interface IProductRepository {
    Iterator<Product> findAll();
    Product findById(String id);
    Product create(Product product);
    Product update(Product product);
    void delete(String id);
}
