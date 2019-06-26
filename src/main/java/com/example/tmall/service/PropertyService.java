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

import com.example.tmall.dao.PropertyDAO;
import com.example.tmall.model.Category;
import com.example.tmall.model.Property;
import com.example.tmall.util.Page4Navigator;


/**
 * CRUD
 */
@Service
@CacheConfig(cacheNames = "properties")
public class PropertyService {
    @Autowired PropertyDAO propertyDAO;
    @Autowired CategoryService categoryService;

    // 根据分类id查其拥有的属性
    @Cacheable(key = "'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.getById(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Property> pageFromJPA = propertyDAO.findByCategory(category, pageable);
        return new Page4Navigator<Property>(pageFromJPA, navigatePages);
    }

    // 根据分类id查其拥有的属性
    public List<Property> list(Category category) {
        return propertyDAO.findByCategory(category);
    }

    @CacheEvict(allEntries = true)
    public void add(Property property) {
        propertyDAO.save(property);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        propertyDAO.deleteById(id);
    }

    @Cacheable(key = "'properties-one-'+ #p0")
    public Property getById(int id) {
        return propertyDAO.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    public void update(Property property) {
        propertyDAO.save(property);
    }
}