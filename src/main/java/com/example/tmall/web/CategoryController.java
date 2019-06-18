package com.example.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.example.tmall.pojo.Category;
import com.example.tmall.service.CategoryService;
import com.example.tmall.util.ImageUtil;
import com.example.tmall.util.Page4Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * MVC CategoryController
 * 返回 json
 */
@RestController
public class CategoryController {

    @Autowired CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        return categoryService.list(start, size, 5);
    }

    @PostMapping("/categories")
    public Object add(Category category,MultipartFile image, HttpServletRequest request)throws Exception {
        categoryService.add(category);
        saveOrUpdateImageFile(category, image, request);
        return category;
    }

    public void saveOrUpdateImageFile(Category category, MultipartFile image, HttpServletRequest request) throws Exception {
        File imageFolder = new File(request.getServletContext().getRealPath("/img/category"));
        File file = new File(imageFolder, category.getId()+".jpg");
        System.out.println(file);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }
}