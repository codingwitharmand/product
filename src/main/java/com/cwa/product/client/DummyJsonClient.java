package com.cwa.product.client;

import com.cwa.product.exception.ClientException;
import com.cwa.product.exception.ServerException;
import com.cwa.product.model.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DummyJsonClient {

    private final WebClient webClient;

    public Mono<ProductResponse> getProducts(Integer limit, Integer skip) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products")
                        .queryParamIfPresent("limit", Optional.ofNullable(limit))
                        .queryParamIfPresent("skip", Optional.ofNullable(skip))
                        .build())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new ClientException("Dummy JSON API returned 4xx error")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new ServerException("Dummy JSON API returned 5xx error")))
                .bodyToMono(ProductResponse.class)
                .doOnSuccess(response -> log.info("Retrieved {} products", response.getProducts().size()))
                .doOnError(error -> log.error("Error while calling Dummy JSON API", error));
    }
}
