package com.example.tmall.controller;

import com.example.tmall.model.User;
import com.example.tmall.service.UserService;
import com.example.tmall.util.Page4Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MVC UserController
 * 返回 json
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 删除,增加,更新由用户进行
    @GetMapping("/users")
    public Page4Navigator<User> list(
        @RequestParam(value = "start", defaultValue = "0") int start,
        @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        return userService.list(start, size, 5);
    }
}