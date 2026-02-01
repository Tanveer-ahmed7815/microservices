package com.microservice.inventory_service.service;

import com.microservice.inventory_service.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    public boolean isInStock(String skuCode) {
        List<Inventory> inventoryList = getInventoryList();
        return inventoryList.stream().anyMatch(e -> e.getSkuCode().equalsIgnoreCase(skuCode));
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
