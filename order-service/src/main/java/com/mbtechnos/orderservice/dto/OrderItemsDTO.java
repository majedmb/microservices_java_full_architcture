package com.mbtechnos.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
