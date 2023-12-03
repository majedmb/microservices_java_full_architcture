package com.mbtechnos.orderservice.service;

import com.mbtechnos.orderservice.dto.InventoryResponse;
import com.mbtechnos.orderservice.dto.OrderItemsDTO;
import com.mbtechnos.orderservice.dto.OrderRequest;
import com.mbtechnos.orderservice.event.PlaceOrderEvent;
import com.mbtechnos.orderservice.model.Order;
import com.mbtechnos.orderservice.model.OrderItems;
import com.mbtechnos.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, PlaceOrderEvent> kafkaTemplate;

        //public void placeOrder(OrderRequest orderRequest){
        public String placeOrder(OrderRequest orderRequest){
        List<OrderItems> orderItems = orderRequest.getOrderItemsListDTO()
                .stream()
                .map(this::maptoOrderItems).toList();
        Order order1 = Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                .orderItemsList(orderItems)
                                .build();

        List<String> skuCodes = order1.getOrderItemsList().stream().map(OrderItems::getSkuCode)
                .toList();
        // call inventory service
        // check if the product exists in stock = call inventory service //
      InventoryResponse[] inventoryResponsesArray =  webClient.build().get()
              //  .uri("http://localhost:8083/api/inventory",
                         .uri("http://inventory-service/api/inventory", //mapping web ApiGateway routes
                        uriBuilder -> uriBuilder.queryParam("skucodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); // get existing products in one block

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock); // all IsInstock = true

        if (allProductsInStock){
            orderRepository.save(order1);
            kafkaTemplate.send("notificationOrderTopic",new PlaceOrderEvent(order1.getOrderNumber())); // send kafka msg to the kafka consumer (notification-service)
            log.info("order {} was saved", order1.getOrderNumber());
            return "Order placed Successfully.........";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try later");
        }

   ///////////////
        /*
        InventoryResponse[] inventoryResponsesArray2;
        try {
            inventoryResponsesArray2 =  webClient.get()
                    .uri("http://localhost:8083/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skucodes", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
            boolean allProductsInStock = Arrays.stream(inventoryResponsesArray2)
                    .allMatch(InventoryResponse::isInStock); // all IsInstock = true
            if (allProductsInStock){
                orderRepository.save(order1);
                log.info("order {} was saved", order1.getOrderNumber());
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("Product don't exist in stock, Try later.......");
        }
    */    //////////////////
    }






    private OrderItems maptoOrderItems(OrderItemsDTO orderItemsDTO) {
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(orderItemsDTO.getPrice());
        orderItems.setSkuCode(orderItemsDTO.getSkuCode());
        orderItems.setQuantity(orderItemsDTO.getQuantity());
        return orderItems;
    }

    public List<Order> getAllOrdersList() {
        return orderRepository.findAll();
    }
}
