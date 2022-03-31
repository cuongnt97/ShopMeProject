package com.shopme.admin.user;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

import static com.shopme.common.Constants.*;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listUserFirstPage(Model model) {
        return listUserByPage(DEFAULT_PAGE_NUM, model, "firstName", "asc");
    }

    @GetMapping("users/page/{pageNum}")
    public String listUserByPage(@PathVariable(name = "pageNum") int pageNum
            , Model model
            , @Param("sortField") String sortField
            , @Param("sortDir") String sortDir){

        System.out.println("Sort Field " + sortField);
        System.out.println("Sort Order " + sortDir);

        Page<User> pageUsers = service.listByPage(pageNum, sortField, sortDir);
        List<User> listUsers = pageUsers.getContent();

        long startCount = (pageNum - 1) * USERS_PER_PAGE + 1;
        long endCount = startCount + USERS_PER_PAGE - 1;

        if (endCount > pageUsers.getTotalElements()){
            endCount = pageUsers.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("lastPage",(int) pageUsers.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", pageUsers
                .getTotalElements());
        model.addAttribute("listUsers", listUsers);
        return "list_users";
    }

    @GetMapping("/users/new")
    public String createNewUser(Model model) {
        List<Role> listRoles = service.getListRoles();
        User user = new User();
        user.setEnable(true);

        model.addAttribute("listRoles", listRoles);
        model.addAttribute(user);
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user
            , RedirectAttributes redirectAttributes
            , @RequestParam("image")MultipartFile multipartFile) throws IOException {
        boolean isCreatingUser = (user.getRecid() == null ||user.getRecid() == 0);
        if (!multipartFile.isEmpty()){
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(filename);
            User savedUser = service.saveUser(user);
            String uploadDir = "user-photos/" + savedUser.getRecid();
            FileUploadUtil.cleanDirectory(uploadDir);
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        } else {
            if (user.getPhoto().isEmpty()) user.setPhoto(null);
            service.saveUser(user);
        }

        if (isCreatingUser) {
            redirectAttributes.addFlashAttribute("message", "The user has been added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "The user has been edited successfully.");
        }

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes, Model model) throws UserNotFoundException {
        try {
            User user = service.getUserById(id);
            List<Role> listRoles = service.getListRoles();
            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Edit User");
            return "user_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }


    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes, Model model) throws UserNotFoundException {
        try {
            service.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "Delete user successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/users";
    }

    @GetMapping("/users/{recid}/enable/{enable}")
    public String updateEnableStatusUser(@PathVariable("recid") Integer recid,
                                         @PathVariable("enable") boolean enable,
                                         RedirectAttributes redirectAttributes) {
        service.updateEnableStatusUser(recid, enable);
        String status = enable ? "enabled" : "disabled";
        redirectAttributes.addFlashAttribute("message", "User has been " + status);

        return "redirect:/users";
    }

}
