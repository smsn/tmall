package com.example.tmall.dao;

import java.util.List;

import com.example.tmall.model.Order;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.Product;
import com.example.tmall.model.User;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * ORM JPA DAO数据访问对象
 * JPA是一套ORM接口规范，Hibernate实现了JPA接口
 * Spring Data JPA 可以理解为 JPA 规范的再次封装抽象
 * Spring Data JPA -> Hibernate -> JDBC -> SQL
 */
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
    // 查询某个订单里的产品
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    // 查询某产品的记录
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}