package com.shopme.admin.user;

import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAllUser(){
        return (List<User>) userRepository.findAll();
    }
}
