package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.tmall.dao.UserDAO;
import com.example.tmall.model.User;
import com.example.tmall.util.Page4Navigator;

/**
 * CRUD
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    // 分页查询
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<User> pageFromJPA = userDAO.findAll(pageable);
        return new Page4Navigator<User>(pageFromJPA, navigatePages);
    }
}