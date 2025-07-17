package com.pritam.journalApp.controller;

import com.pritam.journalApp.Utils.JwtUtil;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.service.UserDetailsServiceImpl;
import com.pritam.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs", description = "Public Controller - helthCheck, signup, login")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    @Operation(summary = "Health Check")
    public String helthCheck() {
        return "Server is running.........";
    }

    @PostMapping("/signup")
    @Operation(summary = "Create a new user (username, password , email(optional), sentimentAnalysis(true/false)(optional))")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try{
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    @Operation(summary = "Login (username, password)")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(), user.getPassword()
                    )
            );
            UserDetails userDetails = userDetailsServiceimpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while logging in {} : {} " ,user.getUserName() , e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
