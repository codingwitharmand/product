package com.cwa.product.controller;

import com.cwa.product.model.ProductResponse;
import com.cwa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Mono<ProductResponse> getProducts(
            @RequestParam(defaultValue = "30", required = false) Integer limit,
            @RequestParam(defaultValue = "0", required = false) Integer skip
    ) {
        return productService.getProducts(limit, skip);
    }

    @GetMapping("/analytics/price-by-category")
    public Mono<Map<String, BigDecimal>> getAveragePriceByCategory() {
        return productService.getAveragePriceByCategory();
    }
}
