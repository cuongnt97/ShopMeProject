package com.shopme.admin.brand;

import com.shopme.admin.exception.BrandNotFoundException;
import com.shopme.common.entities.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.shopme.common.Constants.BRAND_PER_PAGE;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepo;

    public List<Brand> listAll() {
        return (List<Brand>) brandRepo.findAll();
    }

    public String checkUnique(Integer id, String name) {
        Brand brandByName = brandRepo.findByName(name);
        boolean isCreatingNew = (id == null || id == 0);
        if (isCreatingNew){
            if (brandByName != null) return "Duplicated";
        } else {
            if (brandByName != null && brandByName.getId() != id){
                return "Duplicated";
            }
        }
        return "OK";
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

    public Page<Brand> listByPage(int pageNum, String sortDir, String keyword) {

        Sort sort = Sort.by("name");

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, BRAND_PER_PAGE, sort);

        if (keyword != null) {
            return brandRepo.findAll(keyword, pageable);
        }
        return brandRepo.findAll(pageable);

    }
}
