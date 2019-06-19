package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tmall.dao.ProductDAO;
import com.example.tmall.model.Category;
import com.example.tmall.model.Product;
import com.example.tmall.util.Page4Navigator;

/**
 * CRUD
 */
@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryService categoryService;

    // 根据分类id查其拥有的属产品
    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.getById(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Product> pageFromJPA = productDAO.findByCategory(category, pageable);
        return new Page4Navigator<Product>(pageFromJPA, navigatePages);
    }

    public void add(Product product) {
        productDAO.save(product);
    }

    public void delete(int id) {
        productDAO.deleteById(id);
    }

    public Product getById(int id) {
        return productDAO.findById(id).get();
    }

    public void update(Product product) {
        productDAO.save(product);
    }
}