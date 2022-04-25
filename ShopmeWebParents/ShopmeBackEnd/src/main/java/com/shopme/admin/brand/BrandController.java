package com.shopme.admin.brand;

import com.shopme.admin.category.CategoryService;
import com.shopme.admin.common.FileUploadUtil;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.List;

import static com.shopme.common.Constants.BRAND_PER_PAGE;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService cateService;

    @GetMapping("/brands")
    public String listFirstPage(String sortDir, Model model) {

        return listBrandsByPage(1, sortDir, model, null);
    }

    @GetMapping("/brands/page/{pageNum}")
    public String listBrandsByPage(@PathVariable("pageNum") int pageNum
                                   , @Param("sortDir") String sortDir
                                   , Model model
                                   , @Param("keyword") String keyword ){

        if (sortDir == null || sortDir.isEmpty()){
            sortDir = "asc";
        }
        Page<Brand> pageBrands = brandService.listByPage(pageNum, sortDir, keyword);

        List<Brand> listBrands = pageBrands.getContent();

        long startCount = (pageNum - 1) * BRAND_PER_PAGE + 1;
        long endCount = startCount + BRAND_PER_PAGE - 1;

        if (endCount > pageBrands.getTotalElements()){
            endCount = pageBrands.getTotalElements();
        }

        //Get the reverse order of order field
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("reverseSortDir", reverseSortDir);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPage", pageBrands.getTotalPages());
        model.addAttribute("totalElements", pageBrands.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("sortField","name");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
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