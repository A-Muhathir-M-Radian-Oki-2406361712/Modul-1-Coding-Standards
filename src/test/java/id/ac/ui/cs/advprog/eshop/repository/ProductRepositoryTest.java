package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    private Product createProduct(String id, String name, int quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }

    @Test
    void testCreateAndFind() {
        Product product = createProduct("a15", "Sampo Cap Bambang", 100);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals("a15", savedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }

    @Test
    void testCreateIfProductIdNull() {
        Product product = createProduct(null, "Sabun", 10);

        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct.getProductId());
        assertEquals("Sabun", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
    }

    @Test
    void testCreateIfProductNull() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productRepository.create(null)
        );

        assertEquals("Failed to create product: Product cannot be null", exception.getMessage());
    }

    @Test
    void testFindByIdIfNotNull() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("a15");

        assertNotNull(foundProduct);
        assertEquals("a15", foundProduct.getProductId());
        assertEquals("Sampo Bango", foundProduct.getProductName());
        assertEquals(1, foundProduct.getProductQuantity());
    }

    @Test
    void testFindByIdIfNull() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        productRepository.create(product);

        assertNull(productRepository.findById("a16"));
    }

    @Test
    void testFindAll() {
        Product product1 = createProduct("a15", "Sampo Bango", 1);
        Product product2 = createProduct("a16", "Pasta Gigi", 2);

        productRepository.create(product1);
        productRepository.create(product2);

        Iterator<Product> iterator = productRepository.findAll();

        assertTrue(iterator.hasNext());
        Product firstProduct = iterator.next();
        assertEquals("a15", firstProduct.getProductId());
        assertEquals("Sampo Bango", firstProduct.getProductName());

        assertTrue(iterator.hasNext());
        Product secondProduct = iterator.next();
        assertEquals("a16", secondProduct.getProductId());
        assertEquals("Pasta Gigi", secondProduct.getProductName());

        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> iterator = productRepository.findAll();

        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }

    @Test
    void testUpdateIfSuccess() {
        Product oldProduct = createProduct("a15", "Sampo Bango", 1);
        productRepository.create(oldProduct);

        Product updatedProduct = createProduct("a15", "Sampo Bango Refill", 5);
        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("a15", result.getProductId());
        assertEquals("Sampo Bango Refill", result.getProductName());
        assertEquals(5, result.getProductQuantity());

        Product foundProduct = productRepository.findById("a15");
        assertNotNull(foundProduct);
        assertEquals("Sampo Bango Refill", foundProduct.getProductName());
        assertEquals(5, foundProduct.getProductQuantity());
    }

    @Test
    void testUpdateIfProductNotFound() {
        Product product = createProduct("a99", "Produk Tidak Ada", 1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productRepository.update(product)
        );

        assertEquals("Product with ID a99 not found", exception.getMessage());
    }

    @Test
    void testDeleteIfSuccess() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        productRepository.create(product);

        productRepository.delete("a15");

        assertNull(productRepository.findById("a15"));
    }

    @Test
    void testDeleteIfProductNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productRepository.delete("a404")
        );

        assertEquals("Product with ID a404 not found for deletion", exception.getMessage());
    }
}