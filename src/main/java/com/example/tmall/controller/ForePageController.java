package com.example.tmall.controller;

import javax.servlet.http.HttpSession;

import com.example.tmall.model.Order;
import com.example.tmall.model.User;
import com.example.tmall.service.OrderService;
import com.example.tmall.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ForePageController
 */
@Controller
public class ForePageController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String index() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home() {
        return "fore/home";
    }

    @GetMapping("/register")
    public String register() {
        return "fore/register";
    }

    @GetMapping("/registerSuccess")
    public String registerSuccess() {
        return "fore/registerSuccess";
    }

    @GetMapping("/login")
    public String login() {
        return "fore/login";
    }

    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }

    @GetMapping("/product")
    public String product() {
        return "fore/product";
    }

    @GetMapping("/category")
    public String category() {
        return "fore/category";
    }

    @GetMapping("/search")
    public String search() {
        return "fore/search";
    }

    @GetMapping(value = "/buy")
    public String buy() {
        return "fore/buy";
    }

    @GetMapping(value = "/cart")
    public String cart() {
        return "fore/cart";
    }

    @GetMapping(value = "/alipay")
    public String alipay() {
        return "fore/alipay";
    }

    @GetMapping(value = "/payed")
    public String payed() {
        return "fore/payed";
    }

    @GetMapping(value = "/bought")
    public String bought() {
        return "fore/bought";
    }

    @GetMapping(value = "/confirmPay")
    public String confirmPay() {
        return "fore/confirmPay";
    }

    @GetMapping(value = "/orderConfirmed")
    public String orderConfirmed() {
        return "fore/orderConfirmed";
    }

    @GetMapping(value = "/review")
    public String review(HttpSession session, int oid) {
        User user_ = (User) session.getAttribute("user");
        if (null == user_) {
            return "redirect:login";
        }
        User user = userService.getUserByName(user_.getName());
        Order order = orderService.getById(oid);
        if (user.getId() == order.getUser().getId()) {
            return "fore/review";
        }
        return "redirect:login";
    }
}