package com.shopme.admin.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandRestController {

    @Autowired
    private BrandService service;

    @PostMapping("/brands/check_unique")
    public String checkDuplicatedBrand(@Param("id") int id, @Param("name") String name) {
        return service.checkBrandUnique(id, name) ? "OK" : "Duplicated";
    }
}
