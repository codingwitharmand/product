package com.cwa.product.controller;

import com.cwa.product.entity.Product;
import com.cwa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@RequestBody Product product, @PathVariable String id) {
        return productRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(product.getName());
                    existing.setPrice(product.getPrice());
                    return productRepository.save(existing);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable String id) {
        return productRepository.findById(id);
    }
}
