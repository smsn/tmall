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

import com.example.tmall.dao.CategoryDAO;
import com.example.tmall.model.Category;
import com.example.tmall.util.Page4Navigator;


/**
 * CRUD
 * value 缓存的名称
 * key 缓存的 key
 */
@Service
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;

    // 分页查询
    @Cacheable(key = "'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Category> pageFromJPA = categoryDAO.findAll(pageable);
        return new Page4Navigator<Category>(pageFromJPA, navigatePages);
    }

    @Cacheable(key = "'categories-all'")
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    // 添加分类
    @CacheEvict(allEntries = true) //删除 categories~keys 里的所有的keys
    public void add(Category category) {
        categoryDAO.save(category);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        categoryDAO.deleteById(id);
    }

    @Cacheable(key = "'categories-one-'+ #p0")
    public Category getById(int id) {
        return categoryDAO.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    public void update(Category category) {
        categoryDAO.save(category);
    }
}