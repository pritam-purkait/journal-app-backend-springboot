package com.pritam.journalApp.controller;

import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.service.JournalEntryService;
import com.pritam.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs", description = "Journal Entry Controller - getAllJournalEntriesOfUser, createJournalEntry, findJournalEntryById, deleteEntryId, updateJournalEntry")
public class JournalEntryControllerMongodb {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    //working fine
    @GetMapping
    @Operation(summary = "Get all journal entries of the current user")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {

        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.getByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>("No journal entries of this user :(",HttpStatus.NOT_FOUND);
    }
    //working fine
    @PostMapping
    @Operation(summary = "Create a new journal entry (title, content) ")
    public ResponseEntity<JournalEntry> createJournalEntry
            (@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry( myEntry , userName );

            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    //working fine
    @GetMapping("id/{myId}")
    @Operation(summary = "Get a journal entry by id")
    public ResponseEntity<JournalEntry> findJournalEntryById
            (@PathVariable String myId) {

        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.getByUserName(userName);
        List<JournalEntry> collect=
                user.getJournalEntries().stream()
                        .filter(x -> x.getId().equals(myId))
                        .collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry= journalEntryService.getJournalEntryById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    @Operation(summary = "Delete a journal entry by id")
    public  ResponseEntity<?> deleteEntryId
            (@PathVariable String myId) {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteJournalEntryById(myId , userName);
        if(removed)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    @Operation(summary = "Update a journal entry by id")
    public ResponseEntity<?> updateJournalEntry(@RequestBody JournalEntry myEntry, @PathVariable String myId ) {
                Authentication authentication = SecurityContextHolder
                        .getContext().getAuthentication();
                String userName = authentication.getName();

                User user = userService.getByUserName(userName);
                List<JournalEntry> collect=
                        user.getJournalEntries().stream()
                                .filter(x -> x.getId().equals(myId))
                                .collect(Collectors.toList());

                if(!collect.isEmpty()){
                    Optional<JournalEntry> journalEntry= journalEntryService.getJournalEntryById(myId);
                    if (journalEntry.isPresent()) {
                        JournalEntry old = journalEntry.get();

                        old.setTitle(myEntry.getTitle() != null  &&  !myEntry.getTitle().equals("")    ? myEntry.getTitle() : old.getTitle() );
                        old.setContent(myEntry.getContent() != null  &&  !myEntry.getContent().equals("")   ? myEntry.getContent() : old.getContent() );
                        old.setSentiment(myEntry.getSentiment() != null  &&  !myEntry.getSentiment().equals("")   ? myEntry.getSentiment() : old.getSentiment());
                        journalEntryService.saveEntry( old );
                        return new ResponseEntity<>(old, HttpStatus.OK);
                    }
                }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
