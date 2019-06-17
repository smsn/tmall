package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.tmall.dao.CategoryDAO;
import com.example.tmall.pojo.Category;


/**
 * CRUD
 */
@Service
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;

    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

}