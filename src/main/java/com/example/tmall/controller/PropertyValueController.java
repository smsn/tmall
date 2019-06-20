package com.example.tmall.controller;

import java.util.List;

import com.example.tmall.model.Product;
import com.example.tmall.model.PropertyValue;
import com.example.tmall.service.ProductService;
import com.example.tmall.service.PropertyValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MVC PropertyValueController
 */
@RestController
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) {
        Product product = productService.getById(pid);
        propertyValueService.init(product);
        return propertyValueService.list(product);
    }

    @PutMapping("/propertyValues")
    public PropertyValue update(@RequestBody PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return propertyValue;
    }
}