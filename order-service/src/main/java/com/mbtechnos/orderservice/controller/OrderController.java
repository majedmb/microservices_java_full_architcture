package com.mbtechnos.orderservice.controller;

import com.mbtechnos.orderservice.dto.OrderRequest;
import com.mbtechnos.orderservice.model.Order;
import com.mbtechnos.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory1", fallbackMethod = "fallbackMethod") //resilience4j
    @TimeLimiter(name = "inventory1") //resilience4j
    @Retry(name = "inventory1") //resilience4j instance = inventory1
    //public String placeOrder(@RequestBody OrderRequest orderRequest){
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("the order sended {} ", orderRequest);
        //orderService.placeOrder(orderRequest);
      return   CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest)); //execute in separated thread
        //return "order palced succesfully...";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrder(){
       return orderService.getAllOrdersList();
    }

    public CompletableFuture<String>  fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
       // return "We are terrably sorry, something went wrong !";
        return CompletableFuture.supplyAsync(()-> "We are terrably sorry, something went wrong !");
    }
}
