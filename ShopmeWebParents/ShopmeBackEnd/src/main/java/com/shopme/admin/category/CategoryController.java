package com.shopme.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @Autowired
    public CategoryService service;

    @GetMapping("/categories")
    public String listCategoryFirstPage(Model model){

        return "category/list_categories";
    }
}
