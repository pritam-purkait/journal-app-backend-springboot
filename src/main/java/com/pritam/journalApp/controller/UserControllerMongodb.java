package com.pritam.journalApp.controller;


import com.pritam.journalApp.entity.User;

import com.pritam.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/user")
public class UserControllerMongodb {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getUsers() {

        List<User> all = userService.getAll();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //working fine
    @PutMapping
    public ResponseEntity<?> updateUser
            (@RequestBody User user) {

          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          String userName = authentication.getName();

        User userInDb = userService.getByUserName(userName);

            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //working fine
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder
                                         .getContext()
                                         .getAuthentication();

        userService.deleteByUserName(authentication.getName());
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
