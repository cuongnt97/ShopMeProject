package com.shopme.admin.product;

import com.shopme.common.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> listAll(){
        return (List<Product>) productRepo.findAll();
    }

    public Product saveProduct(Product product){
        if (product.getId() == null){
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()){
            String alias = product.getName().replaceAll(" ", "-");
            product.setAlias(alias);
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "-"));

        }
        product.setUpdatedTime(new Date());

        return productRepo.save(product);
    }
}
