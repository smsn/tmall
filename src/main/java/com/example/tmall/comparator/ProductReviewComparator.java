package com.example.tmall.comparator;

import java.util.Comparator;

import com.example.tmall.model.Product;

/**
 * ProductAllComparator
 */
public class ProductReviewComparator implements Comparator<Product> {

    // 把 评价数量多的放前面
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount() - p1.getReviewCount();
    }
}