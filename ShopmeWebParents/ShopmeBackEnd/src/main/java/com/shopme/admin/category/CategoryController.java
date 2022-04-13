package com.shopme.admin.category;

import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    public CategoryService service;

    @GetMapping("/categories")
    public String listCategoryFirstPage(Model model){
        List<Category> listCategories = service.getListCategories();
        model.addAttribute("listCategories", listCategories);
        return "category/list_categories";
    }

    @GetMapping("/categories/new")
    public String createNewCategory(Model model) {
        List<Category> listCategories = service.listCateUserdInForm();


        model.addAttribute("listCategories", listCategories);
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Create Category");
        return "category/category_form";
    }
}
