package com.example.tmall.controller;

import java.util.List;

import com.example.tmall.model.Category;
import com.example.tmall.service.CategoryService;
import com.example.tmall.service.ForeRESTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ForeRESTController
 */
@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ForeRESTService foreRESTService;

    // 返回一个携带产品信息的分类列表
    @GetMapping("/forehome")
    public List<Category> home() {
        List<Category> categories = categoryService.list();
        // 携带产品
        foreRESTService.carryProducts(categories);
        return categories;
    }

}