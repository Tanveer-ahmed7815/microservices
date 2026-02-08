package com.microservice.order_service.service;

import com.microservice.order_service.dto.InventoryResponse;
import com.microservice.order_service.dto.OrderLineItemsDto;
import com.microservice.order_service.dto.OrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final WebClient.Builder webClientBuilder;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<Boolean> placeOrder(OrderRequest orderRequest) {
        try {
            InventoryResponse[] skuCodes = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode",
                                    orderRequest.getOrderLineItemsDtoList()
                                            .stream()
                                            .map(OrderLineItemsDto::getSkuCode)
                                            .toList()).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean b = Arrays.stream(skuCodes)
                    .allMatch(InventoryResponse::isInStock);
            return CompletableFuture.supplyAsync(() -> b);

        } catch (Exception ex) {
            throw new RuntimeException("Inventory service failed", ex);
        }
    }


    public CompletableFuture<Boolean> fallback(OrderRequest req, Throwable t) {
        log.error("Inventory service unavailable", t);
        return CompletableFuture.supplyAsync(() -> false);
    }

}
