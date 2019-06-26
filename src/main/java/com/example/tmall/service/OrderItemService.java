package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.tmall.dao.OrderItemDAO;
import com.example.tmall.model.Order;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.Product;
import com.example.tmall.model.User;

/**
 * CRUD
 */
@Service
@CacheConfig(cacheNames = "orderItems")
public class OrderItemService {
    @Autowired
    private OrderItemDAO orderItemDAO;

    // 查询某个订单里的产品
    @Cacheable(key = "'orderItems-oid'+#p0.id")
    public List<OrderItem> listByOrder(Order order) {
        return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

    // 查询某产品的记录
    @Cacheable(key = "'orderItems-pid-'+#p0.id")
    public List<OrderItem> listByProduct(Product product) {
        return orderItemDAO.findByProduct(product);
    }

    @Cacheable(key = "'orderItems-uid'+#p0.id")
    public List<OrderItem> listByUser(User user) {
        return orderItemDAO.findByUserAndOrderIsNull(user);
    }

    @CacheEvict(allEntries = true)
    public void update(OrderItem orderItem) {
        orderItemDAO.save(orderItem);
    }

    @CacheEvict(allEntries = true)
    public void add(OrderItem orderItem) {
        orderItemDAO.save(orderItem);
    }

    @Cacheable(key = "'orderItems-one-'+#p0")
    public OrderItem getById(int id) {
        return orderItemDAO.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        orderItemDAO.deleteById(id);
    }
}