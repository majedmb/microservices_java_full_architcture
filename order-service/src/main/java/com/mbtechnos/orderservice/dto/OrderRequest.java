package com.mbtechnos.orderservice.dto;

import com.mbtechnos.orderservice.model.OrderItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String orderNumber;
    private List<OrderItemsDTO> orderItemsListDTO;
}
