package com.shopme.admin.brand;

import com.shopme.common.entities.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b WHERE b.name = :name")
    Brand findByName(String name);

    Integer countById(Integer id);
}
