package com.example.tmall.comparator;

import java.util.Comparator;

import com.example.tmall.model.Product;

/**
 * ProductAllComparator
 */
public class ProductDateComparator implements Comparator<Product> {

    // 把 创建日期晚的放前面
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}