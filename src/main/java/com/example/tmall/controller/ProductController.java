package com.example.tmall.controller;

import java.util.Date;

import com.example.tmall.model.Product;
import com.example.tmall.service.ProductImageService;
import com.example.tmall.service.ProductService;
import com.example.tmall.util.Page4Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    // 获取属于cid的产品
    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Product> page = productService.list(cid, start, size, 5);
        productImageService.setFirstProductImages(page.getContent());
        return page;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        Product product = productService.getById(id);
        return product;
    }

    @PostMapping("/products")
    public Product add(@RequestBody Product product) {
        product.setCreateDate(new Date());
        productService.add(product);
        return product;
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id) {
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    public Product update(@RequestBody Product product) {
        productService.update(product);
        return product;
    }
}