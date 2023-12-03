package com.mbtech.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryRequest {
    private String skuCode;
    private Integer quantity;
}
