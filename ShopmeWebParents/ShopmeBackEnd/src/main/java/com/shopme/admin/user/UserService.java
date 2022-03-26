package com.shopme.admin.user;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getListUsers(){
        return (List<User>) userRepo.findAll();
    }

    public void saveUser(User user){
        boolean isUpdatingUser = (user.getUserId() != null);

        if (isUpdatingUser){
            User existingUser = userRepo.findById(user.getUserId()).get();
            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            } else {
                endcodePass(user);
            }
        } else {
            endcodePass(user);
        }
        userRepo.save(user);
    }

    public List<Role> getListRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    private void endcodePass(User user){
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
    }

    public boolean isEmailUnique(Integer userId, String email){
        System.out.println("UserService57 " + userId);
        System.out.println("UserService58 " + email);
        User userByEmail = userRepo.getUserByEmail(email);
        System.out.println("UserService59 " +userByEmail.getUserId());
        if(userByEmail == null) {
            return true;
        }

        boolean isCreatingNew = (userId == null);

        if (isCreatingNew) {
            if (userByEmail != null) {
                return false;
            }
        } else {
            if (userByEmail.getUserId() != userId) {
                return false;
            }
        }
        return true;
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Could not find any user with ID: " + id);
        }

    }

    public void deleteUser(Integer userId) throws UserNotFoundException {
        Integer count = userRepo.countByUserId(userId);
        if (count == null || count == 0){
            throw new UserNotFoundException("Could not find any user with Id: " + userId);
        }
        userRepo.deleteById(userId);
    }

}
