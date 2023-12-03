package com.mbtech.inventoryservice.controller;

import com.mbtech.inventoryservice.dto.InventoryResponse;
import com.mbtech.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public String inStock(@PathVariable("sku-code") String skucode){
       return inventoryService.inStock(skucode);

    }
    //
    @GetMapping("/skus")
    @ResponseStatus(HttpStatus.OK)
    public String inStock(@RequestParam List<String> skucodes){
        return "inventoryService.inStockList(skucodes)";

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skucodes){
        return inventoryService.isInStock(skucodes); //list of products
    }
}
