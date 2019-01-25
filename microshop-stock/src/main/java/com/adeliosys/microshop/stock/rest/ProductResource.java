package com.adeliosys.microshop.stock.rest;

import com.adeliosys.microshop.stock.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public List getProducts() {
        return service.getProducts();
    }
}
