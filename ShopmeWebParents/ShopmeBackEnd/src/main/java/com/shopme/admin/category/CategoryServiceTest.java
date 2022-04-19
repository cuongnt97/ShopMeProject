package com.shopme.admin.category;

import com.shopme.common.entities.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

    @Test
    //Test in case creating new check duplicate by Name
    public void testCheckUniqueName(){
        Integer id = null;
        String name = "computers";
        String alias = "abc";

        Category category = new Category();
        category.setName(name);
        category.setId(id);
        category.setAlias(alias);

        Mockito.when(repo.findByName(name)).thenReturn(category);
        Mockito.when(repo.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        System.out.println("result " + result);
    }

    @Test
    //Test in case creating new check duplicate by Alias
    public void testCheckUniqueAlias(){
        Integer id = null;
        String name = "abc";
        String alias = "electronics";

        Category category = new Category();
        category.setName(name);
        category.setId(id);
        category.setAlias(alias);

        Mockito.when(repo.findByName(name)).thenReturn(null);
        Mockito.when(repo.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        System.out.println("result " + result);
    }

    @Test
    public void testCheckUniqueEditMode(){
        Integer id = 2;
        String name = "camera & photo";
        String alias = "camera";

        Category category = new Category();
        category.setName(name);
        category.setId(id);
        category.setAlias(alias);

        Mockito.when(repo.findByName(name)).thenReturn(null);//Change the value of then Return() to category to test in case checkByName
        Mockito.when(repo.findByAlias(alias)).thenReturn(category);//Change the value of then Return() to null to test in case checkByName
        String result = service.checkUnique(3, name, alias);//Change the parameter # id to test in case duplicate

        System.out.println("result " + result);
    }
}
