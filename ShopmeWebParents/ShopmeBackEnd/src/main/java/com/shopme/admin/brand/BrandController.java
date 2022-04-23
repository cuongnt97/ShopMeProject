package com.shopme.admin.brand;

import com.shopme.admin.category.CategoryService;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService cateService;

    @GetMapping("/brands")
    public String listBrand(Model model) {
        List<Brand> listBrands = brandService.listAll();

        model.addAttribute("listBrands", listBrands);
        return "brand/list_brands";
    }

    @GetMapping("/brands/new")
    public String createNewBrand(Model model) {
        List<Category> listCategories = cateService.listCateUserdInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create Brand");

        return "brand/brand_form";
    }
}