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

import com.example.tmall.dao.UserDAO;
import com.example.tmall.model.User;
import com.example.tmall.util.Page4Navigator;

/**
 * CRUD
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserService {
    @Autowired
    UserDAO userDAO;

    // 分页查询
    @Cacheable(key = "'users-page-'+#p0+ '-' + #p1")
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<User> pageFromJPA = userDAO.findAll(pageable);
        return new Page4Navigator<User>(pageFromJPA, navigatePages);
    }

    public boolean isisExist(String name) {
        User user = userDAO.findByName(name);
        return null != user;
    }

    @CacheEvict(allEntries = true)
    public void add(User user) {
        userDAO.save(user);
    }

    public boolean verifyUser(User user) {
        User user_ = userDAO.findByName(user.getName());
        if (user.getPassword().equals(user_.getPassword())) {
            return true;
        }
        return false;
    }

    @Cacheable(key = "'users-one-name-'+ #p")
    public User getUserByName(String name) {
        User user = userDAO.findByName(name);
        return user;
    }
}