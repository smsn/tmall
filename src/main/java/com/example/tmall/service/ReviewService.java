package com.example.tmall.service;

import java.util.List;

import com.example.tmall.dao.ReviewDAO;
import com.example.tmall.model.Product;
import com.example.tmall.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ReviewService
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    public void add(Review review ) {
        reviewDAO.save(review);
    }

    public List<Review> list(Product product) {
        List<Review> result = reviewDAO.findByProductOrderByIdDesc(product);
        return result;
    }

    public int getCount(Product product) {
        return reviewDAO.countByProduct(product);
    }
}