package com.microservice.inventory_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {

    private Long id;
    private String skuCode;
    private Integer quantity;

}
