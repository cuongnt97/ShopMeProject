package com.shopme.admin.brand;

import com.shopme.common.entities.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepo;

    public List<Brand> listAll() {
        return (List<Brand>) brandRepo.findAll();
    }
}
