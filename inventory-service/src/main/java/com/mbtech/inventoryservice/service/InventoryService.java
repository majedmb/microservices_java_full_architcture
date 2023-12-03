package com.mbtech.inventoryservice.service;

import com.mbtech.inventoryservice.dto.InventoryResponse;
import com.mbtech.inventoryservice.model.Inventory;
import com.mbtech.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public String inStock(String skuCode){

       // Integer quantity = 0;
        Optional<Inventory> inventory1 = inventoryRepository.findBySkuCode(skuCode);
         return checkQuantityProduct(skuCode)? "YES":"NO"; //
      // return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
    //
   /* public String inStockList(List<String> skuCodes){
        List<Inventory> inventory1 = inventoryRepository.findBySkuCodeIn(skuCodes);
        return checkQuantityProductList(skuCodes).isEmpty()? "No":"Yes";
    }*/

//
  /*  private List<Inventory> checkQuantityProductList(List<String> skuCodes) {
        List<Inventory> inventoryResponses = inventoryRepository.findBySkuCodeIn(skuCodes);
        List<Inventory> listvalide = inventoryResponses.stream().filter(
                inventoryResponse -> inventoryResponse.getQuantity() > 0
        ).toList();

        List<Inventory> listInvalide = inventoryResponses.stream().filter(
                inventoryResponse -> inventoryResponse.getQuantity() < 0
        ).toList();
        listInvalide.forEach(inventory -> inventory.getSkuCode());
        log.info("products is note in stock: ", listInvalide.stream().toList());
            return listvalide;
    }*/

    private boolean checkQuantityProduct(String skuCode){
        //inventoryRepository.findBySkuCodeAndByQuantity(skuCode, quantity);
        return inventoryRepository.findBySkuCode(skuCode)
                .filter(inventory1 -> inventory1.getQuantity()>0).isPresent();
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
       /* log.info("waiting.....started"); //to test retry funct of resilience4j
        Thread.sleep(10000);
        log.info("waiting.....ended");*/
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0) // is in stock
                            .quantity(inventory.getQuantity())
                            .build()
                ).toList();
    }
}
