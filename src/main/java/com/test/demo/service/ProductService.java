package com.test.demo.service;

import com.test.demo.entity.Product;
import com.test.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        logger.trace("Products: " + products);
        return products;
    }

    public Product addProduct(Product product) {
        logger.info("Product added");
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        logger.info("Product found");
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public Product updateProduct(Long id, Product product) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
        logger.debug("Product found");
        productToUpdate.setName(product.getName());
        logger.debug("Product name: " + product.getName());
        productToUpdate.setPrice(product.getPrice());
        logger.debug("Product price: " + product.getPrice());
        return productRepository.save(productToUpdate);
    }

    public void deleteProduct(Long id) {
        Product productToDelete = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
        logger.trace(productToDelete.getName() + " deleted");
        productRepository.deleteById(productToDelete.getId());
    }
}
