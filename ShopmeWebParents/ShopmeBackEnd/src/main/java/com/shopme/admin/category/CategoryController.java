package com.shopme.admin.category;

import com.shopme.admin.common.FileUploadUtil;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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

    @PostMapping("/categories/save")
    public String saveCategory(Category category
                                , Model model
                                , @RequestParam("fileImage") MultipartFile multipartFile
                                , RedirectAttributes redirectAttributes) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);
        Category savedCategory = service.saveCategory(category);

        String uploadDir = "../categories-images/" + savedCategory.getId();
        FileUploadUtil.saveFile(uploadDir,fileName, multipartFile);
        redirectAttributes.addFlashAttribute("message", "Category has been added!");
        return "redirect:/categories";
    }
}
