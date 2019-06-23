package com.example.tmall.service;

import java.util.ArrayList;
import java.util.List;

import com.example.tmall.model.Category;
import com.example.tmall.model.Product;
import com.example.tmall.model.ProductImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ForeRESTService
 */
@Service
public class ForeRESTService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ReviewService reviewService;

    public void carryProducts(List<Category> categories) {
        for (Category category : categories) {
            carryProducts(category);
        }
    }

    public void carryProducts(Category category) {
        List<Product> products = productService.listAll(category);
        // 设置封面
        productImageService.setFirstProductImages(products);
        // 移除产品的分类信息
        for (Product product : products) {
            product.setCategory(null);
        }
        // 绑定产品到分类上
        category.setProducts(products);
    }

    public void carrySplitGroupProducts(List<Category> categories) {
        for (Category category : categories) {
            carrySplitGroupProducts(category, category.getProducts());
        }
    }

    public void carrySplitGroupProducts(Category category, List<Product> products) {
        // 对产品分组
        List<List<Product>> productsByRow = splitGroup(products);
        // 绑定分组后的产品
        category.setProductsByRow(productsByRow);
    }

    public List<List<Product>> splitGroup(List<Product> products) {
        // 每组个数
        int productNumberEachRow = 8;
        List<List<Product>> productsByRow = new ArrayList<>();
        for (int i = 0; i < products.size(); i += productNumberEachRow) {
            int toIndex = i + productNumberEachRow;
            toIndex = toIndex > products.size() ? products.size() : toIndex;
            List<Product> productsOfEachRow = products.subList(i, toIndex);
            productsByRow.add(productsOfEachRow);
        }
        return productsByRow;
    }

    public void initProduct(List<Product> products) {
        for (Product product : products) {
            initProduct(product);
        }
    }

    public void initProduct(Product product) {
        // 设置产品预览图
        List<ProductImage> productSingleImages = productImageService.listProductImages(product, "single");
        product.setProductSingleImages(productSingleImages);
        // 设置封面
        product.setFirstProductImage(productSingleImages.get(0));
        // 设置产品详情图片
        List<ProductImage> productDetailImages = productImageService.listProductImages(product, "detail");
        product.setProductDetailImages(productDetailImages);
        //设置产品的销量和评价数量
        setSaleAndReviewNumber(product);
    }

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    public void setSaleAndReviewNumber(Product product) {
        //设置产品的销量和评价数量
        int saleCount = productService.getSaleCount(product);
        int reviewCount = reviewService.getCount(product);
        product.setSaleCount(saleCount);
        product.setReviewCount(reviewCount);
    }

}