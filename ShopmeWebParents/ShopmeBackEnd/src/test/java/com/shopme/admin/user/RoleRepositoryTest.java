package com.shopme.admin.user;

import com.shopme.common.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateRestRoles() {
        Role roleSalesperson = new Role();
        roleSalesperson.setName("Salesperson");
        roleSalesperson.setDescription("Manage product price, customers, shipping, orders and sales report");

        Role roleEditor = new Role();
        roleEditor.setName("Editor");
        roleEditor.setDescription("Manage categories, brands, products, articles and menus");

        Role roleShipper = new Role();
        roleShipper.setName("Shipper");
        roleShipper.setDescription("View products, view orders and update order status");

        Role roleAssistant = new Role();
        roleAssistant.setName("Assistant");
        roleAssistant.setDescription("Manage questions and reviews");

        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }

}
