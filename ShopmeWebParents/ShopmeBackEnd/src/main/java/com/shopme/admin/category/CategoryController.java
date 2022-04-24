package com.shopme.admin.category;

import com.shopme.admin.common.FileUploadUtil;
import com.shopme.admin.user.export.UserCSVExporter;
import com.shopme.common.entities.Category;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.shopme.common.Constants.CATEGORY_PER_PAGE;

@Controller
public class CategoryController {

    @Autowired
    public CategoryService service;

    @GetMapping("/categories")
    public String listFirstPageCategories(String sortDir
                                    , Model model) {

        return listCategoriesByPage(1, sortDir, model, null);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listCategoriesByPage(@PathVariable("pageNum") int pageNum
                             , @Param("sortDir") String sortDir
                             , Model model
                             , @Param("keyword") String keyword){

        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";
        }

        CategoryPageDetails pageDetails = new CategoryPageDetails();

        List<Category> listCategories = service.listCategories(pageDetails, pageNum, sortDir, keyword);

        int startCount = (pageNum - 1) * CATEGORY_PER_PAGE + 1;
        int endCount = startCount + CATEGORY_PER_PAGE -1;

        if (endCount > (int) pageDetails.getTotalElements()){
            endCount = (int) pageDetails.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";


        model.addAttribute("reverseSortDir", reverseSortDir);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPage", pageDetails.getTotalPages());
        model.addAttribute("totalElements", pageDetails.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("sortField","name");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);


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
            String categoryImageForm = multipartFile.getOriginalFilename();

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

    @GetMapping("/categories/{id}/enable/{enable}")
    public String updateStatusCategory(@PathVariable("id") Integer id
                                       , @PathVariable("enable") boolean enable
                                       , RedirectAttributes redirectAttributes){
        service.updateEnableStatusCategory(id, enable);
        String status = enable ? "enabled" : "disabled";

        redirectAttributes.addFlashAttribute("message", "The category ID " + id + " has been " + status );
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(value = "id") Integer id
            , RedirectAttributes redirectAttributes) {
        try {

            //Delete category with id
            service.deleteCategory(id);
            //Declare path for category's photo
            String uploadDir = "category-images/" + id;
            //Delete category's photo
            FileUploadUtil.removeDir(uploadDir);
            //Notice delete success
            redirectAttributes.addFlashAttribute("message", "Delete category successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/categories";
    }

    //Export list category to csv
    @GetMapping("/categories/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        //Get list category
        List<Category> listCategories = service.listCateUserdInForm();
        //Declare new categoryCSVExporter object
        CategoryCSVExporter exporter = new CategoryCSVExporter();
        //Export to csv by categoryCSVExporter object through method exportCSV
        exporter.exportCSV(listCategories, response);
    }

}
