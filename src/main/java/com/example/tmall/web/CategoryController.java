package com.example.tmall.web;

import java.util.List;

import com.example.tmall.pojo.Category;
import com.example.tmall.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MVC CategoryController
 * 返回 json
 */
@RestController
public class CategoryController {

    @Autowired CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list() {
        return categoryService.list();
    }
}