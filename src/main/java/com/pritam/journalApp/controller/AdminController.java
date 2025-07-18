package com.pritam.journalApp.controller;

import com.pritam.journalApp.Cache.AppCache;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs", description = "Admin Controller - getAllUsers, CreateAdmin, clearAppCache")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    @Operation(summary = "Get all users (ADMIN USER REQUIRED)")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userService.getAll();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    @Operation(summary = "Create an admin user (ADMIN USER REQUIRED)")
    public void CreateAdmin(@RequestBody User user) {
        userService.saveAdmin(user);
    }

    @GetMapping("/clear-app-cache")
    @Operation(summary = "Clear app cache (ADMIN USER REQUIRED)")
    public void clearAppCache(){
        appCache.init();
    }
}
