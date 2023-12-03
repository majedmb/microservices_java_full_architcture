package com.demoproductservice.productservice.services;


import com.demoproductservice.productservice.model.Product;
import com.demoproductservice.productservice.dto.ProductResponse;
import com.demoproductservice.productservice.dto.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.demoproductservice.productservice.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
   // @Autowired
    private final ProductRepository productRepository;

    public void creatProduct (ProductRequest productrequest){
        Product product = new Product();
        product.setName(productrequest.getName());
        product.setDescription(productrequest.getDescription());
        product.setPrice(productrequest.getPrice());
        productRepository.save(product);
        System.out.println("new product created"+product.getName());
    }

    public void creatProd(ProductRequest productrequest){
        Product product = Product.builder()
                .name(productrequest.getName())
                .description(productrequest.getDescription())
                .price(productrequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("new product {} created ", product.getName());
    }


    public List<ProductResponse> getAllProds() {
       List<Product> products = productRepository.findAll();
      return products.stream().map(this::maptoProductResponse).toList();
    }

    private ProductResponse maptoProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .description(product.getDescription())
                .price(product.getPrice())
                .name(product.getName())
                .build();
        return productResponse;
    }
}
