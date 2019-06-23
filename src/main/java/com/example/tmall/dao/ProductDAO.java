package com.example.tmall.dao;

import java.util.List;

import com.example.tmall.model.Category;
import com.example.tmall.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ORM JPA DAO数据访问对象 JPA是一套ORM接口规范，Hibernate实现了JPA接口 Spring Data JPA 可以理解为 JPA
 * 规范的再次封装抽象 Spring Data JPA -> Hibernate -> JDBC -> SQL
 */
public interface ProductDAO extends JpaRepository<Product, Integer> {

    Page<Product> findByCategory(Category category, Pageable pageable);
    List<Product> findByCategoryOrderById(Category category);
    List<Product> findByNameLike(String string, Pageable pageable);
}