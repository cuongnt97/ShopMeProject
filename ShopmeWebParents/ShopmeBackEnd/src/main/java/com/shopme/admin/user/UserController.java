package com.shopme.admin.user;

import com.shopme.admin.common.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.export.UserCSVExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.export.UserPDFExporter;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.shopme.common.Constants.*;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    //Access to list user screen
    @GetMapping("/users")
    public String listUserFirstPage(Model model) {
        return listUserByPage(DEFAULT_PAGE_NUM, model, "recid", "asc", null);
    }

    //Paging list user screen
    @GetMapping("users/page/{pageNum}")
    public String listUserByPage(@PathVariable(name = "pageNum") int pageNum
            , Model model
            , @Param("sortField") String sortField
            , @Param("sortDir") String sortDir
            , @Param("keyword") String keyword){

        //Get page user in the page by pageNum, sort after which field and which order (asc/desc)
        Page<User> pageUsers = service.listByPage(pageNum, sortField, sortDir, keyword);
        //Get list user by page
        List<User> listUsers = pageUsers.getContent();
        //Get first user in the page
        long startCount = (pageNum - 1) * USERS_PER_PAGE + 1;
        //Get last user in the page
        long endCount = startCount + USERS_PER_PAGE - 1;

        if (endCount > pageUsers.getTotalElements()){
            endCount = pageUsers.getTotalElements();
        }

        //Get the reverse order of order field
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        //Parameters
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);

        //Pageable
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPage", pageUsers.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalElements", pageUsers
                .getTotalElements());

        //User list
        model.addAttribute("listUsers", listUsers);

        return "user/list_users";
    }

    //Forward to create user screen
    @GetMapping("/users/new")
    public String createNewUser(Model model) {
        //Get list role to show in the screen
        List<Role> listRoles = service.getListRoles();
        //Create new user object
        User user = new User();
        //Set default enable = true
        user.setEnable(true);

        //Send to server by model
        model.addAttribute("listRoles", listRoles);
        model.addAttribute(user);
        model.addAttribute("pageTitle", "Create User");
        return "user/user_form";
    }


    //Confirm create or update an user
    @PostMapping("/users/save")
    public String saveUser(User user
                            , RedirectAttributes redirectAttributes
                            , @RequestParam("image")MultipartFile multipartFile) throws IOException {

        //Declare boolean variable if user is created or updated
        boolean isCreatingUser = (user.getRecid() == null ||user.getRecid() == 0);
        //If upload photo of user
        if (!multipartFile.isEmpty()){
            //Get the name of photo file
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(filename);
            User savedUser = service.saveUser(user);

            //Declare path for user's photo
            String uploadDir = "ShopmeBackEnd/user-photos/" + savedUser.getRecid();
            //Clean old path (if exist)
            FileUploadUtil.cleanDirectory(uploadDir);
            //Save new photo file
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        } else {
            //If user without photo
            if (user.getPhoto().isEmpty()) user.setPhoto(null);
            service.saveUser(user);
        }
        //If user is created
        if (isCreatingUser) {
            //Add message create user success
            redirectAttributes.addFlashAttribute("message"
                    , "The user has been added successfully.");
        } else {
            //Add message update user success
            redirectAttributes.addFlashAttribute("message"
                    , "The user has been updated successfully.");
        }
        //Redirect to list user screen
        return getRedirectURLSavedUser(user);
    }
    //Method get path to redirect list user screen
    private String getRedirectURLSavedUser(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1?sortField=recid&sortDir=asc&keyword=" + firstPartOfEmail;
    }

    //Forward to edit user screen
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(value = "id") Integer id
                            , RedirectAttributes redirectAttributes
                            , Model model) {
        try {
            //Get user by id
            User user = service.getUserById(id);
            //Get list roles to show on edit user screen
            List<Role> listRoles = service.getListRoles();

            //Send to server by model
            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Edit User");
            return "user/user_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }


    }

    //Delete an user
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer id
                            , RedirectAttributes redirectAttributes) {
        try {

            //Delete user with id
            service.deleteUser(id);
            //Declare path for user's photo
            String uploadDir = "ShopmeBackEnd/user-photos/" + id;
            //Delete user's photo
            FileUploadUtil.removeDir(uploadDir);
            //Notice delete success
            redirectAttributes.addFlashAttribute("message", "Delete user successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/users";
    }

    //Set enable/disable an user
    @GetMapping("/users/{recid}/enable/{enable}")
    public String updateEnableStatusUser(@PathVariable("recid") Integer recid
                                         , @PathVariable("enable") boolean enable
                                         , RedirectAttributes redirectAttributes) {
        //Update status user
        service.updateEnableStatusUser(recid, enable);
        String status = enable ? "enabled" : "disabled";
        //Send a notification
        redirectAttributes.addFlashAttribute("message", "The user ID " + recid + " has been " + status);

        return "redirect:/users";
    }

    //Export list user to csv
    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        //Get list users
        List<User> listUsers = service.getListUsers();
        //Declare new UserCSVExporter object
        UserCSVExporter exporter = new UserCSVExporter();
        //Export to csv by UserCSVExporter object through method exportCSV
        exporter.exportCSV(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
        List<User> listUsers = service.getListUsers();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.exportExcel(listUsers, response);

    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<User> listUsers = service.getListUsers();
        UserPDFExporter exporter = new UserPDFExporter();
        exporter.exportPDF(listUsers, response);
    }

    //User view detail information
    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser
                                , Model model) {
        //Get email
        String email = loggedUser.getUsername();
        //Get user by email
        User user = service.getUserByEmail(email);

        //Send to server by model
        model.addAttribute("user", user);
        return "user/account_form";
    }


    //Users update their details themselves
    @PostMapping("/account/update")
    public String saveAccount(User user
                                , RedirectAttributes redirectAttributes
                                , @RequestParam("image")MultipartFile multipartFile
                                , @AuthenticationPrincipal ShopmeUserDetails loggedUser) throws IOException {
        //If user has photo
        if (!multipartFile.isEmpty()){
            //Get the name of user photo
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            //Set to user
            user.setPhoto(filename);
            //Update to database
            User savedUser = service.updateAccount(user);

            //Update folder path of user's photo
            String uploadDir = "user-photos/" + savedUser.getRecid();
            FileUploadUtil.cleanDirectory(uploadDir);
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        } else {
            //If user has no photo
            if (user.getPhoto().isEmpty()) user.setPhoto(null);
            service.updateAccount(user);
        }

        //Send name to server to show on nav-bar
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        //Send notice update success
        redirectAttributes.addFlashAttribute("message"
                , "Your account details have been updated.");


        return "redirect:/account";
    }

}
