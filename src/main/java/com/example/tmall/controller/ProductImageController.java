package com.example.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.example.tmall.model.Product;
import com.example.tmall.model.ProductImage;
import com.example.tmall.service.ProductImageService;
import com.example.tmall.service.ProductService;
import com.example.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * MVC ProductImageController 返回 json
 */
@RestController
public class ProductImageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@RequestParam(value = "type") String type, @PathVariable(value = "pid") int pid) {
        Product product = productService.getById(pid);
        return productImageService.listProductImages(product, type);
    }

    @PostMapping("/productImages")
    public ProductImage add(
        @RequestParam(value = "pid") int pid,
        @RequestParam(value = "type") String type,
        MultipartFile image,
        HttpServletRequest request
        ) throws Exception {

        // 添加到数据库
        ProductImage productImage = new ProductImage();
        Product product = productService.getById(pid);
        productImage.setProduct(product);
        productImage.setType(type);
        productImageService.add(productImage);

        // 保存图片
        String folder = "img/";
        if (ProductImageService.type_single.equals(productImage.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, productImage.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 缩略图
        if (ProductImageService.type_single.equals(productImage.getType())) {
            String fileName = file.getName();
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
        return productImage;
    }

    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        ProductImage productImage = productImageService.getById(id);

        String folder = "img/";
        if (ProductImageService.type_single.equals(productImage.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        // 删除缩略图
        if (ProductImageService.type_single.equals(productImage.getType())) {
            String fileName = file.getName();
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }

        productImageService.delete(id);
        return null;
    }
}