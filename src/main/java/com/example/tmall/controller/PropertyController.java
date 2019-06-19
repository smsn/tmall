package com.example.tmall.controller;

import com.example.tmall.model.Property;
import com.example.tmall.service.PropertyService;
import com.example.tmall.util.Page4Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * PropertyController
 */
@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // 获取cid的属性
    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        return propertyService.list(cid, start, size, 5);
    }

    @GetMapping("/properties/{id}")
    public Property getProperty(@PathVariable("id") int id) {
        Property property = propertyService.getById(id);
        return property;
    }

    @PostMapping("/properties")
    public Property add(@RequestBody Property property) {
        propertyService.add(property);
        return property;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id) {
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    public Property update(@RequestBody Property property) {
        propertyService.update(property);
        return property;
    }
}