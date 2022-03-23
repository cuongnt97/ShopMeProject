package com.shopme.admin.user;

import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createUser(User user){

        userRepo.save(user);
    }

    public List<Role> getListRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    private void endcodePass(User user){
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
    }

    public boolean isEmailUnique(String email){
        User userByEmail = userRepo.getUserByEmail(email);
        return userByEmail == null;
    }

}
