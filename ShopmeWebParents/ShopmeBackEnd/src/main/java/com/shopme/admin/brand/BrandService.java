package com.shopme.admin.brand;

import com.shopme.admin.exception.BrandNotFoundException;
import com.shopme.common.entities.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepo;

    public List<Brand> listAll() {
        return (List<Brand>) brandRepo.findAll();
    }

    public boolean checkBrandUnique(Integer id, String name) {
        Brand brandByName = brandRepo.getBrandByName(name);

        if (brandByName == null) {
            return true;
        }

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            return brandByName == null;
        } else {
            return brandByName.getId() == id;
        }
    }

    public Brand saveBrand(Brand brand) {
        return brandRepo.save(brand);
    }

    public Brand getBrandById(int id) throws BrandNotFoundException {
        try {
            return brandRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new BrandNotFoundException("Could not find this brand");
        }

    }

    public void deleteBrand(Integer id) throws BrandNotFoundException {

        Integer count = brandRepo.countById(id);

        if (count == null || count == 0) {
            throw new BrandNotFoundException("Could not find any brand with ID " + id);
        }
        brandRepo.deleteById(id);
    }
}
