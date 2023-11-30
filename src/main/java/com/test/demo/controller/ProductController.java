package com.test.demo.controller;

import com.test.demo.entity.Product;
import com.test.demo.service.ProductService;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<?> getAllProducts() {
        try {
            return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ErrorHandlerController().handleException(e);
        }
    }

    @PostMapping("/")
    @ResponseBody
    //validates the request body
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ErrorHandlerController().handleException(e);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ErrorHandlerController().handleException(e);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
        } catch (Exception e) {
            return new ErrorHandlerController().handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ErrorHandlerController().handleException(e);
        }
    }
}
