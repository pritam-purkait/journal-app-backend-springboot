package com.pritam.journalApp.controller;

import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.entity.User;
import com.pritam.journalApp.service.JournalEntryService;
import com.pritam.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerMongodb {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    /*
     * CONTROLLER -->
     *           SERVICE -->
     *               REPOSITORY .
     *
     */

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {

        User user = userService.getByUserName(userName);

        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createJournalEntry
            (@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try {

            journalEntryService.saveEntry( myEntry , userName );

            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> findJournalEntryById
            (@PathVariable ObjectId myId) {

        Optional<JournalEntry> journalEntry= journalEntryService.getJournalEntryById(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public  ResponseEntity<?> deleteEntryId(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteJournalEntryById(myId , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(
            @RequestBody JournalEntry myEntry,
            @PathVariable ObjectId myId,
            @PathVariable String userName
    ) {

        //ObjectId objectId = new ObjectId(myId);
        JournalEntry old = journalEntryService.getJournalEntryById(myId).orElse(null);  //objectId

        if(old != null){
            old.setTitle(myEntry.getTitle() != null  &&  !myEntry.getTitle().equals("")    ? myEntry.getTitle() : old.getTitle() );
            old.setContent(myEntry.getContent() != null  &&  !myEntry.getContent().equals("")   ? myEntry.getContent() : old.getContent() );

            journalEntryService.saveEntry( old );
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
