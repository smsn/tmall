package com.example.tmall.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.tmall.comparator.ProductAllComparator;
import com.example.tmall.comparator.ProductDateComparator;
import com.example.tmall.comparator.ProductPriceComparator;
import com.example.tmall.comparator.ProductReviewComparator;
import com.example.tmall.comparator.ProductSaleCountComparator;
import com.example.tmall.model.Category;
import com.example.tmall.model.Order;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.Product;
import com.example.tmall.model.PropertyValue;
import com.example.tmall.model.Review;
import com.example.tmall.model.User;
import com.example.tmall.service.CategoryService;
import com.example.tmall.service.ForeRESTService;
import com.example.tmall.service.OrderItemService;
import com.example.tmall.service.OrderService;
import com.example.tmall.service.ProductImageService;
import com.example.tmall.service.ProductService;
import com.example.tmall.service.PropertyValueService;
import com.example.tmall.service.ReviewService;
import com.example.tmall.service.UserService;
import com.example.tmall.util.ResultStatus;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductImageService productImageService;

    // 返回一个携带产品信息的分类列表
    @GetMapping("/forehome")
    public List<Category> home() {
        List<Category> categories = categoryService.list();
        // 携带产品
        foreRESTService.carryProducts(categories);
        foreRESTService.carrySplitGroupProducts(categories);
        return categories;
    }

    @PostMapping(value = "/foreregister")
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

    @PostMapping(value = "/forelogin")
    public Object register(@RequestBody User user, HttpSession session) {
        String name = HtmlUtils.htmlEscape(user.getName());
        user.setName(name);
        // String password = user.getPassword();
        if (userService.verifyUser(user)) {
            session.setAttribute("user", user);
            return ResultStatus.success(user);
        }
        return ResultStatus.fail("账号密码错误");
    }

    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid) {
        Product product = productService.getById(pid);
        //获取产品属性值
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        //获取产品评价
        List<Review> reviews = reviewService.list(product);
        // 完善产品信息
        foreRESTService.initProduct(product);
        HashMap<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("pvs", propertyValues);
        map.put("reviews", reviews);
        return ResultStatus.success(map);
    }

    @GetMapping("/forecheckLogin")
    public Object checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return ResultStatus.success();
        }
        return ResultStatus.fail("未登录");
    }

    @GetMapping("/forecategory/{cid}")
    public Category Category(@PathVariable int cid, String sort) {
        Category category = categoryService.getById(cid);
        foreRESTService.carryProducts(category);
        foreRESTService.setSaleAndReviewNumber(category.getProducts());
        // 排序
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(), new ProductAllComparator());
                    break;
                default:
                    break;
            }
        }
        return category;
    }

    @PostMapping("/foresearch")
    public List<Product> search(String keyword) {
        if (null == keyword) {
            keyword = "";
        }
        List<Product> products = productService.search(keyword, 0, 20);
        foreRESTService.initProduct(products);
        return products;
    }

    // 立即购买
    @GetMapping("/forebuyone")
    public Object buyone(int pid, int num, HttpSession session) {
        return buyoneAndAddCart(pid, num, session);
    }

    private int buyoneAndAddCart(int pid, int num, HttpSession session) {
        Product product = productService.getById(pid);
        User user_ = (User) session.getAttribute("user");
        User user = userService.getUserByName(user_.getName());
        List<OrderItem> orderItems = orderItemService.listByUser(user);

        int oiid = 0;
        boolean found = false;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == product.getId()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                oiid = orderItem.getId();
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setProduct(product);
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }
        return oiid;
    }

    // 提交订单页面
    @GetMapping("/forebuy")
    public Object buy(String[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem orderItem = orderItemService.getById(id);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        productImageService.setFirstProdutImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);
        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return ResultStatus.success(map);
    }

    // 加入购物车
    @GetMapping("/foreaddCart")
    public Object addCart(int pid, int num, HttpSession session) {
        buyoneAndAddCart(pid, num, session);
        return ResultStatus.success();
    }

    // 查看购物车
    @GetMapping("/forecart")
    public List<OrderItem> cart(HttpSession session) {
        User user_ = (User) session.getAttribute("user");
        User user = userService.getUserByName(user_.getName());
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        productImageService.setFirstProdutImagesOnOrderItems(orderItems);
        return orderItems;
    }

    // 购物车更新
    @GetMapping("/forechangeOrderItem")
    public Object changeOrderItem(HttpSession session, int pid, int num) {
        User user_ = (User) session.getAttribute("user");
        if (null == user_) {
            return ResultStatus.fail("未登录");
        }
        User user = userService.getUserByName(user_.getName());
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == pid) {
                orderItem.setNumber(num);
                orderItemService.update(orderItem);
                break;
            }
        }
        return ResultStatus.success();
    }

    // 购物车删除
    @GetMapping("/foredeleteOrderItem")
    public Object deleteOrderItem(HttpSession session, int oiid) {
        User user_ = (User) session.getAttribute("user");
        if (null == user_) {
            return ResultStatus.fail("未登录");
        }
        orderItemService.delete(oiid);
        return ResultStatus.success();
    }

    // 创建订单
    @PostMapping("/forecreateOrder")
    public Object createOrder(@RequestBody Order order, HttpSession session) {
        User user_ = (User) session.getAttribute("user");
        if (null == user_) {
            return ResultStatus.fail("未登录");
        }
        User user = userService.getUserByName(user_.getName());

        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(0,10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        orderService.add(order);

        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);
        return ResultStatus.success(map);
    }

    // 支付
    @GetMapping("/forepayed")
    public Object payed(int oid) {
        Order order = orderService.getById(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }
}