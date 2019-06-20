package com.example.tmall.dao;

import java.util.List;

import com.example.tmall.model.Product;
import com.example.tmall.model.Property;
import com.example.tmall.model.PropertyValue;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ORM JPA DAO数据访问对象 JPA是一套ORM接口规范，Hibernate实现了JPA接口 Spring Data JPA 可以理解为 JPA
 * 规范的再次封装抽象 Spring Data JPA -> Hibernate -> JDBC -> SQL
 */
public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {

    // 获取某产品所有属性值 如 颜色1,颜色2,材料1,材料2
    List<PropertyValue> getByProductOrderByIdDesc(Product product);

    PropertyValue getByPropertyAndProduct(Property property, Product product);
}