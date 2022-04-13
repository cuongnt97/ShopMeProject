package com.shopme.admin.user;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

import static com.shopme.common.Constants.USERS_PER_PAGE;


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

    public Page<User> listByPage(int pageNum, String sortField
                                    , String sortDir, String keyword) {

        //Get the column need to sort
        Sort sort = Sort.by(sortField);
        //Get the order to sort
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        //Declare an object pageable
        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        //List user with searching
        if (keyword != null) {
            return userRepo.findAll(keyword, pageable);
        }
        //List user no searching
        return userRepo.findAll(pageable);
    }

    //Get list roles from database
    public List<Role> getListRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    //Save user (create or update)
    public User saveUser(User user) {
        //Declare a boolean variable for create/update user
        boolean isUpdatingUser = (user.getRecid() != null);
        //If update user
        if (isUpdatingUser) {
            //Get user from database
            User existingUser = userRepo.findById(user.getRecid()).get();
            //If not update user's password
            if (user.getPassword().isEmpty()) {
                //Set old password
                user.setPassword(existingUser.getPassword());
            } else {
                //Encode new password for user
                endcodePass(user);
            }
            //Create new user
        } else {
            //Encode new password for user
            endcodePass(user);
        }
        //Save to database
        return userRepo.save(user);
    }

    //Encode password for user
    private void endcodePass(User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
    }

    //Check email unique
    public boolean isEmailUnique(Integer recid, String email) {
        //Find user by email
        User userByEmail = userRepo.getUserByEmail(email);

        //If can not find user in database
        if (userByEmail == null) {
            return true;
        }
        //Declare boolean variable if create user user
        boolean isCreatingNew = (recid == null);

        //If create new user
        if (isCreatingNew) {
            //Return to true if userByEmail not exist
            return userByEmail == null;
        } else {
            //Return true if userByEmail has id = recid
            return userByEmail.getRecid() == recid;
        }
    }

    //Get user by recid
    public User getUserById(Integer id) throws UserNotFoundException {
        try {
            //Get user by id from db
            return userRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Could not find any user with ID: " + id);
        }
    }

    //Delete user
    public void deleteUser(Integer recid) throws UserNotFoundException {

        //Count if user has id = recid exist in database
        Integer count = userRepo.countByRecid(recid);
        //If user not exist
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any user with ID: " + recid);
        }
        //Delete user
        userRepo.deleteById(recid);
    }

    //Update status of user (enable/disable)
    public void updateEnableStatusUser(Integer recid, boolean status) {
        //Update to database
        userRepo.updateEnableStatus(recid, status);
    }

    //Get user by email
    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    //Update details of user account to database
    public User updateAccount(User userInForm) {
        //Get user from database
        User userInDb = userRepo.findById(userInForm.getRecid()).get();

        //If update password
        if (!userInForm.getPassword().isEmpty()) {
            userInDb.setPassword((userInForm.getPassword()));
            endcodePass(userInDb);
        }

        //If update photo
        if (userInForm.getPhoto() != null) {
            //Set new photo
            userInDb.setPhoto(userInForm.getPhoto());
        }

        //Set firstName and lastName
        userInDb.setFirstName(userInForm.getFirstName());
        userInDb.setLastName(userInForm.getLastName());

        return userRepo.save(userInDb);
    }




}
