package com.mvargas.redis_example.services;

import com.mvargas.redis_example.models.entity.Product;
import com.mvargas.redis_example.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Cacheable(value = "products")
    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    @Cacheable(value = "product", key = "#id")
    public Product findProductById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    @CachePut(value = "product", key = "#product.id")
    @CacheEvict(value = "products", allEntries = true)
    public Product save(Product product) {
        return repository.save(product);
    }

    @CachePut(value = "product", key = "#id")
    @CacheEvict(value = "products", allEntries = true)
    public Product update(Integer id, Product product) {

        var existingProduct = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setEnabled(product.getEnabled());

        return repository.save(existingProduct);
    }

    @CacheEvict(
            value = {"product", "products"},
            key = "#id",
            allEntries = true
    )
    public void delete(Integer id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Product not found: " + id);
        }

        repository.deleteById(id);
    }
}
