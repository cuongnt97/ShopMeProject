package com.shopme.admin.category;

import com.shopme.admin.common.FileUploadUtil;
import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String listCategoryFirstPage(Model model) {
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
            , RedirectAttributes redirectAttributes) throws Exception {

        try {
            boolean isCreatingCategory = (category.getId() == null || category.getId() == 0);

            String categoryImageInDb = category.getId() == null ? null :  service.getCategoryById((int) category.getId()).getImage();
            String categoryImageForm = category.getImage();

            if (!categoryImageForm.equals(categoryImageInDb)) {

                //Get the name of photo file
                String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                category.setImage(filename);
                Category savedCategory = service.saveCategory(category);

                //Declare path for user's photo
                String uploadDir = "category-images/" + savedCategory.getId();
                //Clean old path (if exist)
                FileUploadUtil.cleanDirectory(uploadDir);
                //Save new photo file
                FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
            } else {
                service.saveCategory(category);
            }

            //If user is created
            if (isCreatingCategory) {
                //Add message create user success
                redirectAttributes.addFlashAttribute("message"
                        , "The category has been added successfully.");
            } else {
                //Add message update user success
                redirectAttributes.addFlashAttribute("message"
                        , "The category has been updated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            throw e;
        }

        return "redirect:/categories";
    }


    @GetMapping("/categories/edit/{id}")
    public String updateCategory(Model model
                                , @PathVariable(value = "id") Integer id
                                , RedirectAttributes redirectAttributes) throws Exception {
        try {
            List<Category> listCategories = service.listCateUserdInForm();
            Category category = service.getCategoryById(id);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle", "Edit Category");
            return "category/category_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/categories";
        }
    }
}
