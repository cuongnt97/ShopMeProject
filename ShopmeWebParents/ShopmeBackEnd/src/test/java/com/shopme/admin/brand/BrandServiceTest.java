package com.shopme.admin.brand;


import com.shopme.common.entities.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTest {

    @MockBean
    private BrandRepository brandRepo;

    @InjectMocks
    private BrandService service;

    @Test
    public void TestCheckUniqueName(){
        Integer id = null;
        String name = "Samsung";

        Brand brand = new Brand();
        brand.setName(name);
        brand.setId(id);

        Mockito.when(brandRepo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, name);

        Assertions.assertEquals("Duplicated", result);

    }
}
