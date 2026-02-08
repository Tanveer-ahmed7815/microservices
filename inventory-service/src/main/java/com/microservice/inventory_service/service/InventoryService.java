package com.microservice.inventory_service.service;

import com.microservice.inventory_service.dto.InventoryResponse;
import com.microservice.inventory_service.model.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class InventoryService {

    public List<InventoryResponse> isInStock(List<String> skuCodes) throws InterruptedException {
        List<Inventory> inventoryList = getInventoryList();
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");
        List<String> inventorySkuList = inventoryList.stream().map(Inventory::getSkuCode).toList();
        boolean isPresent = new HashSet<>(inventorySkuList).containsAll(skuCodes);
        if (isPresent) {
            return inventoryList.stream().map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .inStock(inventory.getQuantity() > 0)
                            .build()
            ).toList();
        } else {
            throw new RuntimeException("Invalid Sku Codes");
        }


    }

    private List<Inventory> getInventoryList() {
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(new Inventory(1l, "ABC123", 10));
        inventoryList.add(new Inventory(2l, "DEF123", 20));
        inventoryList.add(new Inventory(3l, "GHI123", 5));
        inventoryList.add(new Inventory(4l, "JKL123", 30));

        return inventoryList;
    }

}
