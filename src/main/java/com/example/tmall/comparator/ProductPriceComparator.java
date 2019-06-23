package com.example.tmall.comparator;

import java.util.Comparator;

import com.example.tmall.model.Product;

/**
 * ProductAllComparator
 */
public class ProductPriceComparator implements Comparator<Product> {

    // 把 价格低的的放前面
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice()-p2.getPromotePrice());
    }
}