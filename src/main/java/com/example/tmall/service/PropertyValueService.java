package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.tmall.dao.PropertyValueDAO;
import com.example.tmall.model.Product;
import com.example.tmall.model.Property;
import com.example.tmall.model.PropertyValue;

/**
 * CRUD
 */
@Service
public class PropertyValueService {
    @Autowired
    PropertyValueDAO propertyValueDAO;

    @Autowired
    PropertyService propertyService;

    public void init(Product product) {
        // 产品的属性(如颜色,材料) 继承自分类
        List<Property> properties = propertyService.list(product.getCategory());
        for (Property property : properties) {
            PropertyValue propertyValue = propertyValueDAO.getByPropertyAndProduct(property, product);
            // 若属性值(如颜色:红色)不存在,初始化一条数据
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);;
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    // 获取某产品所有属性值
    public List<PropertyValue> list(Product product) {
        return propertyValueDAO.getByProductOrderByIdDesc(product);
    }

    // 获取某产品 某个属性的值
    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueDAO.getByPropertyAndProduct(property, product);
    }

    public void update(PropertyValue propertyValue) {
        propertyValueDAO.save(propertyValue);
    }
}