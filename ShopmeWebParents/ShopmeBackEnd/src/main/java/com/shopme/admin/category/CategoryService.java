package com.shopme.admin.category;

import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository cateRepo;

    public List<Category> getListCategories(){
        List<Category> rootCategories = cateRepo.listRootCategories();
        return hierarchicalCategories(rootCategories);
    }

    private List<Category> hierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyCategory(rootCategory));

            Set<Category> children = rootCategory.getChildren();
            for (Category subCategory : children
                 ) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyCategory(subCategory, name));

                subHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }
        return hierarchicalCategories;
    }

    private void subHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel) {
        Set<Category> children = parent.getChildren();

        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            hierarchicalCategories.add(Category.copyCategory(subCategory, name));

            subHierarchicalCategories(hierarchicalCategories, subCategory, subLevel);
        }
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

                    listSubCategoriesUsedInForm(categoriesInForm, subCategory,1);
                }
            }
        }
        return categoriesInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesInForm, Category parent, int sublevel){
        int newSubLevel = sublevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCat : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCat.getName();
            categoriesInForm.add(new Category(name, subCat.getId()));
            listSubCategoriesUsedInForm(categoriesInForm, subCat, newSubLevel + 1);
        }
    }

    public Category getCategoryById(int id) throws CategoryNotFoundException {
        try {
            return cateRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CategoryNotFoundException("Could not find this category");
        }
    }

}
