package com.mvargas.redis_example.controllers;


import com.mvargas.redis_example.models.entity.Product;
import com.mvargas.redis_example.services.ProductNativeService;
import com.mvargas.redis_example.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductNativeService productNativeService;

    public ProductController(ProductService productService,
                             ProductNativeService productNativeService) {
        this.productService = productService;
        this.productNativeService = productNativeService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/native")
    public List<Product> getAllProductsNative() {
        return productNativeService.getAllProductsNative();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> save(
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.save(product)
        );
    }

    @PostMapping("/native")
    public ResponseEntity<Product> saveNative(
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productNativeService.save(product)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Integer id,
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.update(id, product)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
