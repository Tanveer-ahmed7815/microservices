package com.microservice.order_service.service;

import com.microservice.order_service.dto.InventoryResponse;
import com.microservice.order_service.dto.OrderLineItemsDto;
import com.microservice.order_service.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WebClient webClient;

    public boolean placeOrder(OrderRequest orderRequest) {

        //call inventory service, and place order if product is in stock

        List<String> skuCodeList = orderRequest.getOrderLineItemsDtoList().stream()
                .map(OrderLineItemsDto::getSkuCode)
                .toList();

        InventoryResponse[] skuCodes = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        return Arrays.stream(skuCodes)
                .allMatch(InventoryResponse::isInStock);


    }
}
