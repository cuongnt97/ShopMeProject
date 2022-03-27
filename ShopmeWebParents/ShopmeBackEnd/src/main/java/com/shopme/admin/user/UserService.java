package com.shopme.admin.user;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getListUsers() {
        return (List<User>) userRepo.findAll();
    }

    public List<Role> getListRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public User saveUser(User user) {
        boolean isUpdatingUser = (user.getRecid() != null);

        if (isUpdatingUser) {
            User existingUser = userRepo.findById(user.getRecid()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                endcodePass(user);
            }
        } else {
            endcodePass(user);
        }

        return userRepo.save(user);
    }

    private void endcodePass(User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
    }

    public boolean isEmailUnique(Integer recid, String email) {
        //System.out.println("UserService57 " + userId);
        //System.out.println("UserService58 " + email);
        User userByEmail = userRepo.getUserByEmail(email);
        //System.out.println("UserService59 " + userByEmail.getUserId());
        if (userByEmail == null) {
            return true;
        }

        boolean isCreatingNew = (recid == null);

        if (isCreatingNew) {
            return userByEmail == null;
        } else {
            return userByEmail.getRecid() == recid;
        }
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Could not find any user with ID: " + id);
        }

    }

    public void deleteUser(Integer recid) throws UserNotFoundException {
        Integer count = userRepo.countByRecid(recid);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any user with ID: " + recid);
        }
        userRepo.deleteById(recid);
    }

    public void updateEnableStatusUser(Integer recid, boolean status) {
        userRepo.updateEnableStatus(recid, status);
    }


}
