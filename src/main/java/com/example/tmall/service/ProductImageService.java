package com.example.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.tmall.dao.ProductImageDAO;
import com.example.tmall.model.OrderItem;
import com.example.tmall.model.Product;
import com.example.tmall.model.ProductImage;

/**
 * CRUD
 */
@Service
public class ProductImageService {
    @Autowired
    ProductImageDAO productImageDAO;

    @Autowired
    ProductService productService;

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    // 查询产品图片
    public List<ProductImage> listProductImages(Product product, String type) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type);
    }

    public void add(ProductImage productImage) {
        productImageDAO.save(productImage);
    }

    public void delete(int id) {
        productImageDAO.deleteById(id);
    }

    public ProductImage getById(int id) {
        return productImageDAO.findById(id).get();
    }

    // 给产品设置一张图片,封面
    public void setFirstProductImage(Product product) {
        List<ProductImage> singleImages = listProductImages(product, type_single);
        if (!singleImages.isEmpty()) {
            product.setFirstProductImage(singleImages.get(0));
        } else {
            //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片
            product.setFirstProductImage(new ProductImage());
        }
    }

    public void setFirstProductImages(List<Product> products) {
        for (Product product : products) {
            setFirstProductImage(product);
        }
    }

    public void setFirstProdutImagesOnOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setFirstProductImage(orderItem.getProduct());
        }
    }
}