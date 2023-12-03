package com.mbtechnos.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private List<OrderItemsDTO> orderItemsListDTO;
}
