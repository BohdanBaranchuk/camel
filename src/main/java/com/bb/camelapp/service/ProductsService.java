package com.bb.camelapp.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsService {

    public List<Product> fetchProductsByCategory(String category) {
        return List.of(new Product("product1", "category1"));
    }
}
