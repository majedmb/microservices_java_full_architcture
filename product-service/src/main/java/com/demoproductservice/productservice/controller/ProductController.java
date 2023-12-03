package com.demoproductservice.productservice.controller;

import com.demoproductservice.productservice.dto.ProductRequest;
import com.demoproductservice.productservice.dto.ProductResponse;
import com.demoproductservice.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productrequest){
        productService.creatProd(productrequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return  productService.getAllProds();
    }
}
