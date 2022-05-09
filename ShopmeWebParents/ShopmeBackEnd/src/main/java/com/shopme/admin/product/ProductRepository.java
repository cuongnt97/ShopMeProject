package com.shopme.admin.product;

import com.shopme.common.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Product findByName(String name);

    @Query("UPDATE Product p SET p.enable = ?2 WHERE p.id = ?1")
    @Modifying
    public void updateEnableStatus(Integer id, boolean enable);

    Integer countById(Integer id);
}
