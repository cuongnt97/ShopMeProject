package com.shopme.admin.user;

import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listAllUser(Model model) {
        List<User> listUsers = service.listAllUser();
        model.addAttribute("listUsers", listUsers);
        return "list_users";
    }

    @GetMapping("/users/new")
    public String createNewUser(Model model){
        User user = new User();
        model.addAttribute(user);
        return "user_form";
    }

    @PutMapping("/users/save")
    public String saveUser(Model model, User user){
        System.out.println(user);
        return "redirect:/users";
    }



}
