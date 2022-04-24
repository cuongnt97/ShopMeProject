package com.shopme.admin.brand;

import com.shopme.admin.category.CategoryService;
import com.shopme.admin.common.FileUploadUtil;
import com.shopme.common.entities.Brand;
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

    @GetMapping("/brands/edit/{id}")
    public String updateBrand(Model model
                              , @PathVariable(value = "id") Integer id
                              , RedirectAttributes redirectAttributes ){
        try {
            List<Category> listCategories = cateService.listCateUserdInForm();
            Brand brand = brandService.getBrandById(id);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("brand", brand);
            model.addAttribute("pageTitle", "Edit Brand");
            return "brand/brand_form";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/brands";
        }


    }

    @PostMapping("/brands/save")
    public String saveBrand(Brand brand
                            , Model model
                            , @RequestParam("fileImage")MultipartFile multipartFile
                            , RedirectAttributes redirectAttributes) {

        try {
            boolean isCreatingNew = (brand.getId() == null ||brand.getId() == 0);

            if (!multipartFile.isEmpty()){
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                brand.setLogo(fileName);
                Brand savedBrand = brandService.saveBrand(brand);

                String uploadDir = "brand-logos/" + savedBrand.getId();
                FileUploadUtil.cleanDirectory(uploadDir);

                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } else {
                brandService.saveBrand(brand);
            }

            if (isCreatingNew){
                redirectAttributes.addFlashAttribute("message"
                       , "The brand has been added successfully.");
            } else{
                redirectAttributes.addFlashAttribute("message"
                        ,"The brand has been updated successfully." );
            }
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/brands";
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(value = "id") Integer id
                              , RedirectAttributes redirectAttributes ){
        try {
            brandService.deleteBrand(id);

            String uploadDir = "brand-logos/" + id;

            FileUploadUtil.removeDir(uploadDir);
            redirectAttributes.addFlashAttribute("message", "Delete Brand successfully");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }

        return "redirect:/brands";
    }
}