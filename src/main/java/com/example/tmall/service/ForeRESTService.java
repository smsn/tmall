package com.example.tmall.service;

import java.util.ArrayList;
import java.util.List;

import com.example.tmall.model.Category;
import com.example.tmall.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ForeRESTService
 */
@Service
public class ForeRESTService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    public void carryProducts(List<Category> categories) {
        for (Category category : categories) {
            carryProducts(category);
        }
    }

    public void carryProducts(Category category) {
        List<Product> products = productService.listAll(category);
        // 设置封面
        productImageService.setFirstProductImages(products);
        //移除产品的分类信息
        for (Product product : products) {
            product.setCategory(null);
        }
        // 绑定产品到分类上
        category.setProducts(products);
        // 对产品分组
        List<List<Product>> productsByRow = splitGroup(products);
        // 绑定分组后的产品
        category.setProductsByRow(productsByRow);
    }

    public List<List<Product>> splitGroup(List<Product> products) {
        // 每组个数
        int productNumberEachRow = 8;
        List<List<Product>> productsByRow = new ArrayList<>();
        for (int i = 0; i < products.size(); i += productNumberEachRow) {
            int toIndex = i + productNumberEachRow;
            toIndex = toIndex > products.size() ? products.size() : toIndex;
            List<Product> productsOfEachRow = products.subList(i, toIndex);
            productsByRow.add(productsOfEachRow);
        }
        return productsByRow;
    }

}