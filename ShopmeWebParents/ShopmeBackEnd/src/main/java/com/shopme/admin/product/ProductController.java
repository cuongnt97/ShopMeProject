package com.shopme.admin.product;

import com.shopme.admin.brand.BrandService;
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.common.FileUploadUtil;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Product;
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
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService cateService;

    @GetMapping("/products")
    public String listAll(Model model) {
        List<Product> listProducts = service.listAll();

        model.addAttribute("listProducts", listProducts);

        return "product/list_products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        List<Brand> listBrands = brandService.listAll();

        Product product = new Product();
        product.setEnable(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", listBrands);

        model.addAttribute("pageTitle", "Create Product");

        return "product/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product
            , @RequestParam("fileImage") MultipartFile mainImageMultipart
            , @RequestParam("extraImage") MultipartFile[] extraImageMultipart
            , @RequestParam(name = "detailName", required = false) String[] detailName
            , @RequestParam(name = "detailValue", required = false) String[] detailValue
            , RedirectAttributes redirectAttributes) throws IOException {

        try {
            setMainImageName(mainImageMultipart, product);
            setExtraImageNames(extraImageMultipart, product);
            setProductDetails(detailName, detailValue, product);

            boolean isCreatingNew = (product.getId() == null || product.getId() == 0);

            Product savedProduct = service.saveProduct(product);

            saveUploadedImages(mainImageMultipart, extraImageMultipart, savedProduct);

            if (isCreatingNew) {
                redirectAttributes.addFlashAttribute("message"
                        , "The product has been added successfully.");
            } else {
                redirectAttributes.addFlashAttribute("message"
                        , "The product has been updated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/products";
    }

    private void setProductDetails(String[] detailName, String[] detailValue, Product product) {
        if (detailName == null || detailName.length == 0) {
            return;
        }

        for (int count = 0; count < detailName.length; count++) {
            String name = detailName[count];
            String value = detailValue[count];

            if (!name.isEmpty() && !value.isEmpty()) {
                product.addDetail(name, value);
            }
        }
    }

    private void saveUploadedImages(MultipartFile mainImageMultipart
            , MultipartFile[] extraImageMultipart, Product savedProduct) throws IOException {

        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            String uploadDir = "product-images/" + savedProduct.getId();
            FileUploadUtil.cleanDirectory(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);

        }

        if (extraImageMultipart.length > 0) {
            String uploadDir = "product-images/" + savedProduct.getId() + "/extras";
            for (MultipartFile multipartFile : extraImageMultipart) {
                if (multipartFile.isEmpty()){
                    continue;
                }
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        }
    }

    private void setExtraImageNames(MultipartFile[] extraImageMultipart, Product product) {
        if (extraImageMultipart.length > 0) {
            for (MultipartFile multipartFile : extraImageMultipart) {
                if (!multipartFile.isEmpty()) {
                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    product.addExtraImages(fileName);
                }
            }
        }
    }

    private void setMainImageName(MultipartFile mainImageMultipart, Product product) {
        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            product.setMainImage(fileName);
        }
    }

    @GetMapping("/products/{id}/enable/{enable}")
    public String updateStatusProduct(@PathVariable("id") Integer id
            , @PathVariable("enable") boolean enable
            , RedirectAttributes redirectAttributes) {

        service.updateEnableStatusProduct(id, enable);
        String status = enable ? "enabled" : "disabled";

        redirectAttributes.addFlashAttribute("message", "The product ID " + id + " has been " + status);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes) throws Exception {
        try {
            service.deleteProduct(id);
            String extraImageDir = "product-images/" + id + "/extras";
            String mainImageDir = "product-images/" + id;

            FileUploadUtil.removeDir(extraImageDir);
            FileUploadUtil.removeDir(mainImageDir);

            redirectAttributes.addFlashAttribute("message", "Delete product successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/products";
    }
}
