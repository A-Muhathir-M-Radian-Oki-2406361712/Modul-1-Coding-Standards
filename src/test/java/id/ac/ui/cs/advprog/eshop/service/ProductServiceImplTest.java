package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
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
    void testCreate() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        verify(productRepository).create(product);
        assertEquals(product, result);
        assertEquals("a15", result.getProductId());
        assertEquals("Sampo Bango", result.getProductName());
        assertEquals(1, result.getProductQuantity());
    }

    @Test
    void testUpdate() {
        Product product = createProduct("a15", "Sampo Bango Refill", 5);
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        verify(productRepository).update(product);
        assertNotNull(result);
        assertEquals("a15", result.getProductId());
        assertEquals("Sampo Bango Refill", result.getProductName());
        assertEquals(5, result.getProductQuantity());
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete("a15");

        productService.delete("a15");

        verify(productRepository).delete("a15");
    }

    @Test
    void testFindByIdIfNotNull() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        when(productRepository.findById("a15")).thenReturn(product);

        Product result = productService.findById("a15");

        verify(productRepository).findById("a15");
        assertNotNull(result);
        assertEquals("a15", result.getProductId());
        assertEquals("Sampo Bango", result.getProductName());
        assertEquals(1, result.getProductQuantity());
    }

    @Test
    void testFindByIdIfNull() {
        when(productRepository.findById("a16")).thenReturn(null);

        Product result = productService.findById("a16");

        verify(productRepository).findById("a16");
        assertNull(result);
    }

    @Test
    void testFindAll() {
        Product product1 = createProduct("a15", "Sampo Bango", 1);
        Product product2 = createProduct("a16", "Pasta Gigi", 2);

        Iterator<Product> iterator = Arrays.asList(product1, product2).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        verify(productRepository).findAll();
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("a15", result.get(0).getProductId());
        assertEquals("Sampo Bango", result.get(0).getProductName());
        assertEquals(1, result.get(0).getProductQuantity());

        assertEquals("a16", result.get(1).getProductId());
        assertEquals("Pasta Gigi", result.get(1).getProductName());
        assertEquals(2, result.get(1).getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        when(productRepository.findAll()).thenReturn(Collections.<Product>emptyList().iterator());

        List<Product> result = productService.findAll();

        verify(productRepository).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateIfRepositoryThrowsException() {
        Product product = createProduct("x1", "Produk", 1);
        when(productRepository.update(product))
                .thenThrow(new IllegalArgumentException("Product not found"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.update(product)
        );

        assertEquals("Product not found", exception.getMessage());
    }
}