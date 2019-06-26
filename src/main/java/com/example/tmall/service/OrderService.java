package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.tmall.dao.OrderDAO;
import com.example.tmall.model.Order;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.User;
import com.example.tmall.util.Page4Navigator;

/**
 * CRUD
 */
@Service
@CacheConfig(cacheNames = "orders")
public class OrderService {

    public static final String waitPay = "待付款";
    public static final String cancel = "取消";
    public static final String waitDelivery = "待发货";
    public static final String waitConfirm = "待收货";
    public static final String waitReview = "待评价";
    public static final String finish = "完成";
    public static final String delete = "刪除";

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductImageService productImageService;

    public Page4Navigator<Order> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Order> pageFromJPA = orderDAO.findAll(pageable);
        return new Page4Navigator<Order>(pageFromJPA, navigatePages);
    }

    public void init(List<Order> orders) {
        for (Order order : orders) {
            init(order);
        }
    }

    public void init(Order order) {
        List<OrderItem> orderItems = orderItemService.listByOrder(order);
        // 金额
        float total = 0;
        // 产品数
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            // 将订单属性置空
            orderItem.setOrder(null);
            productImageService.setFirstProductImage(orderItem.getProduct());
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            totalNumber += orderItem.getNumber();
        }
        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
    }

    @Cacheable(key = "'orders-one-'+ #p0")
    public Order getById(int id) {
        return orderDAO.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    public void add(Order order) {
        orderDAO.save(order);
    }

    @CacheEvict(allEntries = true)
    public void update(Order order) {
        orderDAO.save(order);
    }

    public List<Order> listByUserWithoutDelete(User user) {
        List<Order> orders = orderDAO.findByUserAndStatusNotOrderByIdDesc(user, OrderService.delete);
        return orders;
    }
}