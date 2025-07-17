package com.pritam.journalApp.service;

import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.getByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            throw new RuntimeException("Error while saving Journal Entry");
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(String id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalEntryById(String id, String userName) {
        boolean removed = false;
        try {
            User user = userService.getByUserName(userName);
             removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error while deleting Journal Entry");
        }
        return removed;
    }



}
/*
* CONTROLLER --> SERVICE --> REPOSITORY
*/