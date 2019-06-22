package com.example.tmall.controller;

import java.util.List;

import com.example.tmall.model.Category;
import com.example.tmall.model.User;
import com.example.tmall.service.CategoryService;
import com.example.tmall.service.ForeRESTService;
import com.example.tmall.service.UserService;
import com.example.tmall.util.ResultStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * ForeRESTController
 */
@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ForeRESTService foreRESTService;

    @Autowired
    private UserService userService;

    // 返回一个携带产品信息的分类列表
    @GetMapping("/forehome")
    public List<Category> home() {
        List<Category> categories = categoryService.list();
        // 携带产品
        foreRESTService.carryProducts(categories);
        return categories;
    }

    @PostMapping(value="/foreregister")
    public Object register(@RequestBody User user) {
        String name = HtmlUtils.htmlEscape(user.getName());
        // String password = user.getPassword();
        user.setName(name);
        if (userService.isisExist(name)) {
            return ResultStatus.fail("用户名已经被使用");
        }
        userService.add(user);
        return ResultStatus.success(user);
    }
}