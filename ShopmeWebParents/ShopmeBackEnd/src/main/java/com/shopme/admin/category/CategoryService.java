package com.shopme.admin.category;

import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository cateRepo;

    public List<Category> getListCategories(){
        return (List<Category>) cateRepo.findAll();
    }

    public Category saveCategory(Category category) {

        return cateRepo.save(category);
    }



    public List<Category> listCateUserdInForm() {
        Iterable<Category> categoriesInDb =  cateRepo.findAll();

        List<Category> categoriesInForm = new ArrayList<>();

        for (Category category : categoriesInDb) {
            if (category.getParent() == null) {
                categoriesInForm.add(new Category(category.getName(), category.getId()));

                Set<Category> children = category.getChildren();

                for (Category subCategory: children) {
                    String name = "--" + subCategory.getName();
                    categoriesInForm.add(new Category(name, subCategory.getId()));

                    listChildren(categoriesInForm, subCategory,1);
                }
            }
        }
        return categoriesInForm;
    }

    private void listChildren(List<Category> categoriesInForm, Category parent, int sublevel){
        int newSubLevel = sublevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCat : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCat.getName();
            categoriesInForm.add(new Category(name, subCat.getId()));
            listChildren(categoriesInForm, subCat, newSubLevel);
        }
    }

}
