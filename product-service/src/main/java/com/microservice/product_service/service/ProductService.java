package com.microservice.product_service.service;

import com.microservice.product_service.dto.ProductRequest;
import com.microservice.product_service.dto.ProductResponse;
import com.microservice.product_service.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id("1")
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        log.info("Product {} is saved ", product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<ProductResponse> productResponses = new ArrayList<>();
        productResponses.add(new ProductResponse("1", "Iphone", "Iphone 17", new BigDecimal(1000)));
        productResponses.add(new ProductResponse("2", "Samsung", "Samsung 11", new BigDecimal(500)));
        productResponses.add(new ProductResponse("3", "Motorolla", "Motoralla 10", new BigDecimal(100)));
        productResponses.add(new ProductResponse("4", "Redmi", "Redmi 1", new BigDecimal(1500)));
        return productResponses;
    }
}
