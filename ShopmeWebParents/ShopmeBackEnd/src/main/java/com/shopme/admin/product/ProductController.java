package com.shopme.admin.product;

import com.shopme.admin.brand.BrandService;
import com.shopme.admin.category.CategoryService;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import com.shopme.common.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveProduct(Product product, RedirectAttributes redirectAttributes) {

        service.saveProduct(product);

        redirectAttributes.addFlashAttribute("message", "Create new product successfully");

        return "redirect:/products";
    }
}
