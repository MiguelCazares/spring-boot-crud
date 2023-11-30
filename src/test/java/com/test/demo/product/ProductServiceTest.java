package com.test.demo.product;

import com.test.demo.entity.Product;
import com.test.demo.repository.ProductRepository;
import com.test.demo.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(1L, "Product 1", "Product 1 description", 100.00F);
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.addProduct(product);
        assert createdProduct.getId() == 1L;
        assert createdProduct.getName().equals("Product 1");
        assert createdProduct.getDescription().equals("Product 1 description");
        assert createdProduct.getPrice() == 100.00F;

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product(1L, "Product 1", "Product 1 description", 100.00F);
        Product product2 = new Product(2L, "Product 2", "Product 2 description", 150.00F);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> productList = productService.getProducts();
        Assertions.assertEquals(2, productList.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        Product product = new Product(1L, "Product 1", "Product 1 description", 100.00F);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        Product foundProduct = productService.getProductById(1L);
        assert foundProduct.getId() == 1L;
        assert foundProduct.getName().equals("Product 1");
        assert foundProduct.getDescription().equals("Product 1 description");
        assert foundProduct.getPrice() == 100.00F;

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(1L, "Product 1", "Product 1 description", 100.00F);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(1L, product);
        assert updatedProduct.getId() == 1L;
        assert updatedProduct.getName().equals("Product 1");
        assert updatedProduct.getDescription().equals("Product 1 description");
        assert updatedProduct.getPrice() == 100.00F;

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(1L, "Product 1", "Product 1 description", 100.00F);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
