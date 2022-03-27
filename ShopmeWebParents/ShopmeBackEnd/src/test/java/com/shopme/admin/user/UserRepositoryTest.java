package com.shopme.admin.user;

import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user = new User();
        user.setEmail("cuong97ndc@gmail.com");
        user.setPassword("1111111111111");
        user.setFirstName("Cuong");
        user.setLastName("Nguyen");
        user.addRole(roleAdmin);

        userRepository.save(user);
    }

    @Test
    public void testCreateUserTwoRoles() {
        Role roleAdmin = new Role(1);
        Role roleEditor = new Role(3);
        User user = new User();
        user.setEmail("cuongndc@gmail.com");
        user.setPassword("1111111111111");
        user.setFirstName("Cuong");
        user.setLastName("Nguyen");
        user.addRole(roleAdmin);
        user.addRole(roleEditor);

        userRepository.save(user);
    }

    @Test
    public void testListUser() {
        Iterable<User> listUser = userRepository.findAll();
        listUser.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User user = userRepository.findById(2).get();
        System.out.println(user);
    }

    @Test
    public void testUpdateUserDetail() {
        User user = userRepository.findById(1).get();
        user.setEnable(true);
        user.setEmail("cuong97ndc@gmail.com");
        userRepository.save(user);
    }

    @Test
    public void testUpdateUserRole() {
        User user = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleShipper = new Role(4);
        user.getRoles().remove(roleEditor);
        user.addRole(roleShipper);

        userRepository.save(user);
    }

    @Test
    public void deleteUser() {
        userRepository.deleteById(2);
    }

    @Test
    public void getUserByEmail() {
        String email = "cuong97ndc@gmail.com";
        User user = userRepository.getUserByEmail(email);
        System.out.println(user);
    }

    @Test
    public void testCountById() {
        Integer integer = 1;
        Integer count = userRepository.countByRecid(integer);
        System.out.println(count + " count test");
    }

    @Test
    public void testUpdateUserStatus() {
        Integer id = 2;
        boolean enable = true;
        //boolean enable= false;
        userRepository.updateEnableStatus(id, enable);
    }
}
