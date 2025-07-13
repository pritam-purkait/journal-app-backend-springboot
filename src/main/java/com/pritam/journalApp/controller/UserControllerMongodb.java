package com.pritam.journalApp.controller;


import com.pritam.journalApp.api.response.QuoteResponse;
import com.pritam.journalApp.api.response.WeatherResponse;
import com.pritam.journalApp.entity.User;

import com.pritam.journalApp.service.QuoteService;
import com.pritam.journalApp.service.UserService;

import com.pritam.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "User Controller - greetings, updateUser, deleteUser")
public class UserControllerMongodb {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private QuoteService quoteService;


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

    @GetMapping
    public ResponseEntity<?> greetings(){

        Authentication authentication = SecurityContextHolder
                                         .getContext()
                                         .getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Kolkata");
        QuoteResponse quoteResponse = quoteService.getQuote();
        String greeting = "Hiee " + authentication.getName() + " >_<  ";
        String quote = " ";
        if(weatherResponse != null || quoteResponse != null){
            quote = quoteResponse.getText()+" ~ "+quoteResponse.getAuthor() + "\n" ;
            greeting = greeting +"\n" +" Temp is "+weatherResponse.getCurrent().getTempC()+"℃ "+"weather feels like "+weatherResponse.getCurrent().getFeelslike()+"℃ .";
        }

        return new ResponseEntity<>(greeting+"\n"+quote , HttpStatus.OK);
    }
}
