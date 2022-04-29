package com.shopme.admin.product;

import com.shopme.common.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> listAll(){
        return (List<Product>) productRepo.findAll();
    }
}
