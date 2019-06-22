package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.tmall.dao.OrderItemDAO;
import com.example.tmall.model.Order;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.Product;

/**
 * CRUD
 */
@Service
public class OrderItemService {
    @Autowired
    private OrderItemDAO orderItemDAO;

    // 查询某个订单里的产品
    public List<OrderItem> listByOrder(Order order) {
        return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

    // 查询某产品的记录
    public List<OrderItem> listByProduct(Product product) {
        return orderItemDAO.findByProduct(product);
    }
}