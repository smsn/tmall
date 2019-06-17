package com.example.tmall.web;

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

    @GetMapping(value = "/admin_category_list")
    public String listCategory() {
        return "/admin/list_category";
    }
}