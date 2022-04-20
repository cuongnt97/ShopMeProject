package com.shopme.admin.category;

import com.shopme.common.entities.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> listRootCategories(Sort sort);

    public Category findByName(String name);

    public Category findByAlias(String alias);

    @Query("UPDATE Category c SET c.enable = ?2 WHERE c.id = ?1")
    @Modifying
    void updateEnableStatus(Integer id, boolean enable);

    Integer countById(Integer id);
}
