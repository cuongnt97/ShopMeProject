package com.shopme.admin.product;

import com.shopme.admin.exception.ProductNotFoundException;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
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

    public String checkUnique(Integer id, String name) {
        Product productByName = productRepo.findByName(name);
        boolean isCreatingNew = (id == null || id == 0);
        if (isCreatingNew){
            if (productByName != null) return "Duplicated";
        } else {
            if (productByName != null && productByName.getId() != id){
                return "Duplicated";
            }
        }
        return "OK";
    }

    public void updateEnableStatusProduct(Integer id, boolean enable) {
        productRepo.updateEnableStatus(id, enable);
    }

    public void deleteProduct(Integer id) throws ProductNotFoundException {
        Integer count = productRepo.countById(id);
        if (count == null || count == 0) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
        productRepo.deleteById(id);
    }
}
