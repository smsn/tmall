package com.example.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 后台管理页面跳转
 */
@Controller
public class AdminPageController {

    @GetMapping(value = "/admin")
    public String admin() {
        return "redirect:/admin_category_list";
    }

    // 分类列表
    @GetMapping(value = "/admin_category_list")
    public String listCategory() {
        return "/admin/list_category";
    }

    // 分类编辑
    @GetMapping(value = "/admin_category_edit")
    public String editCategory() {
        return "/admin/edit_category";
    }

    // 属性列表
    @GetMapping(value = "/admin_property_list")
    public String listProperty() {
        return "admin/list_property";
    }

    // 属性编辑
    @GetMapping(value = "/admin_property_edit")
    public String editProperty() {
        return "admin/edit_property";
    }
}