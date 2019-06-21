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

    // 产品列表
    @GetMapping(value = "/admin_product_list")
    public String listProduct() {
        return "admin/list_product";
    }

    // 产品编辑
    @GetMapping(value = "/admin_product_edit")
    public String editProduct() {
        return "admin/edit_product";
    }

    // 产品图片管理
    @GetMapping(value = "/admin_productImage_list")
    public String listProductImage() {
        return "admin/list_product_image";
    }

    // 产品属性值设置
    @GetMapping(value = "/admin_propertyValue_edit")
    public String editPropertyValue() {
        return "admin/edit_property_value";
    }

    @GetMapping(value = "/admin_user_list")
    public String listUser() {
        return "admin/list_user";
    }

    @GetMapping(value = "/admin_order_list")
    public String listOrder() {
        return "admin/list_order";
    }
}