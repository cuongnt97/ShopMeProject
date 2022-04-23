package com.shopme.admin.brand;

import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepo;

    @Test
    public void testInsertOneCategory(){
        Category laptops = new Category(4);
        Brand brand = new Brand(null, "Apple", "Electronic", new HashSet<>());
        brand.getCategories().add(laptops);

        Brand savedBrand = brandRepo.save(brand);

        System.out.println("Brand name " + savedBrand.getName());
    }

    @Test
    public void testInsertManyCategories(){
        Category cate1 = new Category(1);
        Category cate2 = new Category(2);
        Category cate3 = new Category(3);
        Brand brand = new Brand(null, "LG", "default.png", new HashSet<>());
        brand.getCategories().add(cate1);
        brand.getCategories().add(cate2);

        Brand savedBrand = brandRepo.save(brand);

        System.out.println("Brand name " + savedBrand.getName());

    }

    @Test
    public void testFindAll(){
        Iterable<Brand> brands = brandRepo.findAll();
        for (Brand brand : brands
             ) {
            System.out.println("Brand: " + brand.getName());
        }
    }

    @Test
    public void testFindById(){
        Brand brand = brandRepo.findById(2).get();

        Set<Category> categories = brand.getCategories();
        System.out.println("Category size " + categories.size());
        for (Category category : categories
             ) {
            System.out.println("Category name " + category.getName());
        }
    }

    @Test
    public void testUpdateBrand(){

        Brand brand = brandRepo.findById(2).get();

        brand.setName("CuongNT");

        Brand savedBrand = brandRepo.save(brand);

        System.out.println("Brand After " + savedBrand.getName());

    }

    @Test
    public void testDelete(){

        brandRepo.deleteById(2);

        Optional<Brand> result = brandRepo.findById(2);
        assertThat(result.isEmpty());
    }
}
