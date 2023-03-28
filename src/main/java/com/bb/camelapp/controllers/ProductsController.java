package com.bb.camelapp.controllers;

import com.bb.camelapp.service.Product;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @GetMapping("/products/{category}")
    public List<Product> getProductsByCategory(@PathVariable("category") final String category) {

        producerTemplate.start();

        List<Product> products = producerTemplate.requestBody("direct:fetchProducts", category, List.class);

        producerTemplate.stop();

        return products;
    }
}
