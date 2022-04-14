package com.shopme.admin.category;

import com.shopme.common.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepo;

    @Test
    public void testCreateCategory(){
        Category category = new Category();
        category.setName("Electronics");
        category.setAlias("Electronics");
        category.setEnable(true);
        category.setImage("default.png");
        categoryRepo.save(category);
    }

    @Test
    public void testCreateSubCategory(){
        Category subCategory = new Category();
        subCategory.setName("Iphone");
        subCategory.setAlias("Iphone");
        subCategory.setEnable(true);
        subCategory.setImage("default.png");
        subCategory.setParent(categoryRepo.findById(7).get());
        categoryRepo.save(subCategory);
    }

    @Test
    public void testGetCategory(){
        Category category = categoryRepo.findById(2).get();
        Set<Category> subCategories = category.getChildren();

        for (Category subCat : subCategories
             ) {
            System.out.println(subCat.getName());
        }

    }

    @Test
    public void testPrintHierarchyCat(){
        Iterable<Category> categories = categoryRepo.findAll();
        for (Category cate : categories
             ) {
            if (cate.getParent() == null) {
                System.out.println(cate.getName());

                Set<Category> children = cate.getChildren();
                for (Category cat: children
                     ) {
                    System.out.println("--" + cat.getName());
                    printChildren(cat,1);
                }
            }
        }
    }
    private void printChildren(Category parent, int sublevel){
        int newSubLevel = sublevel + 1;
        Set<Category> children = parent.getChildren();
        for (Category subCat : children) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(subCat.getName());
            printChildren(subCat, newSubLevel);
        }
    }

    @Test
    public void testListRootCategories() {
        List<Category> listTest = categoryRepo.listRootCategories();

        for (Category category : listTest
             ) {
            System.out.println(category.getName());
        }
    }
}
