package com.mbtechnos.orderservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
