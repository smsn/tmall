package com.example.tmall.controller;

import java.util.Date;

import com.example.tmall.model.Order;
import com.example.tmall.service.OrderService;
import com.example.tmall.util.Page4Navigator;
import com.example.tmall.util.ResultStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Page4Navigator<Order> list(
        @RequestParam(value = "start", defaultValue = "0") int start,
        @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Order> page = orderService.list(start, size, 5);
        // 完善订单信息
        orderService.init(page.getContent());
        return page;
    }

    // 发货
    @PutMapping("/deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) {
        Order order = orderService.getById(oid);
        // 开始发货
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return ResultStatus.success();
    }
}