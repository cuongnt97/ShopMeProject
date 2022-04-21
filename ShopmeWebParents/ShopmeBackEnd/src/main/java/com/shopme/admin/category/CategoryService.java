package com.shopme.admin.category;

import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.common.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.shopme.common.Constants.CATEGORY_PER_PAGE;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository cateRepo;

    public List<Category> listCategories(CategoryPageDetails pageDetails, int pageNum, String sortDir){

        Sort sort = Sort.by("name");

        if (sortDir == null || sortDir.isEmpty()){
            sort = sort.ascending();
        } else if(sortDir.equals("asc")){
            sort = sort.ascending();
        } else if (sortDir.equals("desc")){
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, CATEGORY_PER_PAGE, sort);
        Page<Category> pageCategories = cateRepo.listRootCategories(pageable);

        List<Category> rootCategories = pageCategories.getContent();

        pageDetails.setTotalPages(pageCategories.getTotalPages());
        pageDetails.setTotalElements(pageCategories.getTotalElements());

        System.out.println("CategoryService44 totalPage " + pageCategories.getTotalPages());
        System.out.println("CategoryService45 totalElements " + pageCategories.getTotalElements());


        return hierarchicalCategories(rootCategories, sortDir);
    }

    private List<Category> hierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyCategory(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);
            for (Category subCategory : children
                 ) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyCategory(subCategory, name));

                subHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
            }
        }
        return hierarchicalCategories;
    }

    private void subHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel, String sortDir) {
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);

        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            hierarchicalCategories.add(Category.copyCategory(subCategory, name));

            subHierarchicalCategories(hierarchicalCategories, subCategory, subLevel, sortDir);
        }
    }

    public Category saveCategory(Category category) {

        return cateRepo.save(category);
    }


    public List<Category> listCateUserdInForm() {
        Iterable<Category> categoriesInDb =  cateRepo.listRootCategories(Sort.by("name").ascending());

        List<Category> categoriesInForm = new ArrayList<>();

        for (Category category : categoriesInDb) {
            if (category.getParent() == null) {
                categoriesInForm.add(new Category(category.getName(), category.getId()));

                Set<Category> children = sortSubCategories(category.getChildren());

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
        Set<Category> children = sortSubCategories(parent.getChildren());

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

    public String checkUnique(Integer id, String name, String alias) {

        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = cateRepo.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null){
                return "DuplicateName";
            } else {
                Category categoryByAlias = cateRepo.findByAlias(alias);
                if (categoryByAlias != null){
                    return "DuplicateAlias";
                }
            }
        } else {

            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }

            Category categoryByAlias = cateRepo.findByAlias(alias);

            if(categoryByAlias != null && categoryByAlias.getId() != id) {
                return "DuplicateAlias";
            }
        }

        return "OK";
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children) {

        return sortSubCategories(children, "asc");
    }
        private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                if (sortDir.equals("asc")){
                    return o1.getName().compareTo(o2.getName());
                }
                return o2.getName().compareTo(o1.getName());
            }
        });
        sortedChildren.addAll(children);
        return sortedChildren;
    }

    public void updateEnableStatusCategory(Integer id, boolean enable) {
        cateRepo.updateEnableStatus(id, enable);
    }

    public void deleteCategory(Integer id) throws CategoryNotFoundException {

        Integer count = cateRepo.countById(id);

        if (count == null || count == 0){
            throw new CategoryNotFoundException("Could not find any category with ID: " + id);
        }

        cateRepo.deleteById(id);
    }
}
