package com.shopme.admin.user;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listAllUser(Model model) {
        List<User> listUsers = service.getListUsers();
        model.addAttribute("listUsers", listUsers);
        return "list_users";
    }

    @GetMapping("/users/new")
    public String createNewUser(Model model){
        List<Role> listRoles = service.getListRoles();
        User user = new User();
        user.setEnable(true);

        model.addAttribute( "listRoles", listRoles);
        model.addAttribute(user);
        model.addAttribute("pageTitle", "Create New User") ;
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
        service.saveUser(user);
        if (user.getUserId() == null){
            redirectAttributes.addFlashAttribute("message", "The user has been added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "The user has been edited successfully.");
        }

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes, Model model) throws UserNotFoundException {
        try{
            User user = service.getUserById(id);
            List<Role> listRoles = service.getListRoles();
            model.addAttribute("user", user);
            model.addAttribute("listRoles",listRoles);
            model.addAttribute("pageTitle", "Edit User");
            return "user_form";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }


    }

}
