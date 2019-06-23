package com.example.tmall.comparator;

import java.util.Comparator;

import com.example.tmall.model.Product;

/**
 * ProductAllComparator
 */
public class ProductAllComparator implements Comparator<Product> {

    // 把 销量*评价 高的放前面
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
    }
}