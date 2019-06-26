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

import com.example.tmall.dao.ProductDAO;
import com.example.tmall.model.Category;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.Product;
import com.example.tmall.util.Page4Navigator;

/**
 * CRUD
 */
@Service
@CacheConfig(cacheNames = "products")
public class ProductService {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    // 根据分类id查其拥有的属产品
    @Cacheable(key = "'products-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.getById(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Product> pageFromJPA = productDAO.findByCategory(category, pageable);
        return new Page4Navigator<Product>(pageFromJPA, navigatePages);
    }

    public List<Product> listAll(Category category) {
        return productDAO.findByCategoryOrderById(category);
    }

    public List<Product> search(String keyword, int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        List<Product> products = productDAO.findByNameLike("%" + keyword + "%", pageable);
        return products;
    }

    @CacheEvict(allEntries = true)
    public void add(Product product) {
        productDAO.save(product);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        productDAO.deleteById(id);
    }

    @Cacheable(key = "'products-one-'+ #p0")
    public Product getById(int id) {
        return productDAO.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    public void update(Product product) {
        productDAO.save(product);
    }

    public int getSaleCount(Product product) {
        List<OrderItem> orderItems = orderItemService.listByProduct(product);
        int saleCount = 0;
        for (OrderItem orderItem : orderItems) {
            // 存在订单且已支付
            if (null != orderItem.getOrder() && null != orderItem.getOrder().getPayDate()) {
                saleCount += orderItem.getNumber();
            }
        }
        return saleCount;
    }
}