package com.shopme.admin.user;

import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Test
    public void testCreateUser(){
        User user = new User();
        user.setEmail("cuong97ndc@gmail.com");
        user.setPassword("1111111111111");
        user.setFirstName("Cuong");
        user.setLastName("Nguyen");

    }
}
