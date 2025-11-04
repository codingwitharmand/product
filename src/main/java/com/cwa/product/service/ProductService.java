package com.cwa.product.service;

import com.cwa.product.client.DummyJsonClient;
import com.cwa.product.model.Product;
import com.cwa.product.model.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final DummyJsonClient dummyJsonClient;

    public Mono<ProductResponse> getProducts(Integer limit, Integer skip) {
        return dummyJsonClient.getProducts(limit, skip)
                .map(this::enrichProductResponse);
    }

    public Mono<Map<String, BigDecimal>> getAveragePriceByCategory() {
        return dummyJsonClient.getProducts(100, 0)
                .flatMapMany(response -> Flux.fromIterable(response.getProducts()))
                .groupBy(Product::getCategory)
                .flatMap(group -> group
                        .map(Product::getPrice)
                        .filter(Objects::nonNull)
                        .collectList()
                        .map(prices -> {
                            var average = prices.stream()
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    .divide(BigDecimal.valueOf(prices.size()), 2, RoundingMode.HALF_UP);
                            return Map.entry(group.key(), average);
                        })
                ).collectMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    private ProductResponse enrichProductResponse(ProductResponse productResponse) {
        productResponse.setProducts(
                productResponse.getProducts().stream()
                        .map(this::addDiscountPrice)
                        .toList()
        );

        return productResponse;
    }

    private Product addDiscountPrice(Product product) {
        if (Objects.nonNull(product.getDiscountPercentage())
                && product.getDiscountPercentage().compareTo(BigDecimal.ZERO) > 0) {
            var discountAmount = product.getPrice().multiply(product.getDiscountPercentage())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            product.setDiscountedPrice(product.getPrice().subtract(discountAmount));
        }

        return product;
    }
}
