package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService service;

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
    void testCreateProductPage() {
        Model model = new ExtendedModelMap();

        String viewName = productController.createProductPage(model);

        assertEquals("createProduct", viewName);
        assertTrue(model.containsAttribute("product"));
        assertNotNull(model.getAttribute("product"));
        assertInstanceOf(Product.class, model.getAttribute("product"));
    }

    @Test
    void testCreateProductPost() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        Model model = new ExtendedModelMap();

        String viewName = productController.createProductPost(product, model);

        verify(service).create(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testProductListPage() {
        Product product1 = createProduct("a15", "Sampo Bango", 1);
        Product product2 = createProduct("a16", "Pasta Gigi", 2);
        List<Product> products = Arrays.asList(product1, product2);

        when(service.findAll()).thenReturn(products);

        Model model = new ExtendedModelMap();
        String viewName = productController.productListPage(model);

        verify(service).findAll();
        assertEquals("productList", viewName);
        assertTrue(model.containsAttribute("products"));
        assertEquals(products, model.getAttribute("products"));
    }

    @Test
    void testEditProductPage() {
        Product product = createProduct("a15", "Sampo Bango", 1);
        when(service.findById("a15")).thenReturn(product);

        Model model = new ExtendedModelMap();
        String viewName = productController.editProductPage("a15", model);

        verify(service).findById("a15");
        assertEquals("EditProduct", viewName);
        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.getAttribute("product"));
    }

    @Test
    void testEditProductPost() {
        Product product = createProduct("a15", "Sampo Bango Refill", 5);

        String viewName = productController.editProductPost(product);

        verify(service).update(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct("a15");

        verify(service).delete("a15");
        assertEquals("redirect:/product/list", viewName);
    }
}