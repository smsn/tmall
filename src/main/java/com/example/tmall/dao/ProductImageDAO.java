package com.example.tmall.dao;

import java.util.List;

import com.example.tmall.model.Product;
import com.example.tmall.model.ProductImage;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ORM JPA DAO数据访问对象 JPA是一套ORM接口规范，Hibernate实现了JPA接口 Spring Data JPA 可以理解为 JPA
 * 规范的再次封装抽象 Spring Data JPA -> Hibernate -> JDBC -> SQL
 */
public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {

    List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}