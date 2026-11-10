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
public class ProductNativeService {

    private static final String KEY_PREFIX = "products_native:";
    private final RedisTemplate<String, Object> redisTemplate;

    private final ProductRepository repository;

    public List<Product> getAllProductsNative(){

        var cachedProducts = redisTemplate.opsForValue().get(KEY_PREFIX);

        if (cachedProducts != null) {
            return (List<Product>) cachedProducts;
        }

        var products = repository.findAll();

        redisTemplate.opsForValue()
                .set(
                    KEY_PREFIX,
                    products,
                    Duration.ofMinutes(10)
                );

        return products;
    }

    public Product save(Product product) {

        var savedProduct = repository.save(product);

        redisTemplate.opsForValue().set(
                KEY_PREFIX + savedProduct.getId(),
                savedProduct,
                Duration.ofMinutes(10)
        );

        return savedProduct;
    }

    public Product findProductById(Integer id) {
        var cachedProduct = redisTemplate.opsForValue()
                .get(KEY_PREFIX + id);

        if (cachedProduct != null) {
            return (Product) cachedProduct;
        }

        var product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id)
                );

        redisTemplate.opsForValue().set(
                KEY_PREFIX + id,
                product,
                Duration.ofMinutes(10)
        );

        return product;
    }

    public Product update(Integer id, Product product) {

        var existingProduct = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id)
                );

        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setEnabled(product.getEnabled());

        // 1. update BD
        var updatedProduct = repository.save(existingProduct);

        // 2. update Redis
        var key = KEY_PREFIX + updatedProduct.getId();

        redisTemplate.opsForValue().set(
                key,
                updatedProduct,
                Duration.ofMinutes(10)
        );

        return updatedProduct;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        redisTemplate.delete(KEY_PREFIX + id);
    }
}
