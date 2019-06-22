package com.example.tmall.dao;

import java.util.List;

import com.example.tmall.model.Product;
import com.example.tmall.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ReviewDAO
 */
public interface ReviewDAO extends JpaRepository<Review, Integer> {

    List<Review> findByProductOrderByIdDesc(Product product);

    int countByProduct(Product product);
}