package com.shopme.admin.product;

import com.shopme.common.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public String listAll(Model model) {
        List<Product> listProducts = service.listAll();

        model.addAttribute("listProducts", listProducts);

        return "product/list_products";
    }
}
