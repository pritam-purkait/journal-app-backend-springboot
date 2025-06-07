package com.pritam.journalApp.service;

import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.repository.JournalEntryRepository;
import com.pritam.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {


    @Autowired
    private UserRepository ur;


    public void saveEntry(User user) {
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

    public User getByUserName(String userName) {
        return ur.findByUserName(userName);
    }
}
/*
* CONTROLLER -->
*           SERVICE -->
*               REPOSITORY .
*
*/
