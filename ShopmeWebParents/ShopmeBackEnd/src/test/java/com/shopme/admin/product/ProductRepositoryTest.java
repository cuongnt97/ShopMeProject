package com.shopme.admin.product;

import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import com.shopme.common.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager manager;

    @Test
    public void testCreateNew(){

        Brand brand = manager.find(Brand.class, 37);//Samsung
        Category category = manager.find(Category.class, 5);//Cellphone

        Product product = new Product();
        product.setName("Acer Aspire Desktop");
        product.setAlias("acer_aspire_desktop");
        product.setShortDescription("Short description for Acer Aspire");
        product.setFullDescription("Full description for Acer Aspire");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(678);
        product.setCost(600);
        product.setEnable(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAll(){

        Iterable<Product> listProducts = repo.findAll();

        listProducts.forEach(System.out::println);

    }

    @Test
    public void testGetProduct(){

        Product product = repo.findById(2).get();

        System.out.println(product.getName());

        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct(){

        Integer id = 1;

        Product product = repo.findById(id).get();

        product.setName("Test update");

        repo.save(product);

        Product updatedProduct = manager.find(Product.class, id);

        assertThat(updatedProduct.getName().compareTo("Test update"));
    }

    @Test
    public void testDelete(){
        Integer id = 1;
        repo.deleteById(id);

        Optional<Product> product = repo.findById(id);
        assertThat(!product.isPresent());
    }
}
