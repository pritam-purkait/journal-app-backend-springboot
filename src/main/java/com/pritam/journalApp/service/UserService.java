package com.pritam.journalApp.service;

import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.repository.JournalEntryRepository;
import com.pritam.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class UserService {


    @Autowired
    private UserRepository ur;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        ur.save(user);
    }

    public void saveUser(User user) {
        ur.save(user);
    }

    public List<User> getAll() {
        return ur.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return ur.findById(id);
    }

    public void deleteId(ObjectId id) {
        ur.deleteById(id);
    }

    public void deleteByUserName(String userName) {
        ur.deleteByUserName(userName);
    }

    public User getByUserName(String userName) {
        return ur.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        ur.save(user);
    }
}
/*
* CONTROLLER -->
*           SERVICE -->
*               REPOSITORY .
*
*/
