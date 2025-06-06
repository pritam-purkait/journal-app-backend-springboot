package com.pritam.journalApp.controller;

import com.pritam.journalApp.entity.JournalEntry;
import com.pritam.journalApp.service.JournalEntryService;
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

    /*
     * CONTROLLER -->
     *           SERVICE -->
     *               REPOSITORY .
     *
     */

    @GetMapping
    public ResponseEntity<?> getJournalEntries() {

        List<JournalEntry> all = journalEntryService.getAllJournalEntries();
        if (!all.isEmpty() && all != null) {
                return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry
            (@RequestBody JournalEntry myEntry) {
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry( myEntry );

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

    @DeleteMapping("id/{myId}")
    public  ResponseEntity<?> deleteEntryId(@PathVariable ObjectId myId) {
        journalEntryService.deleteJournalEntryById(myId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntry
            (@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){

        JournalEntry old = journalEntryService.getJournalEntryById(myId)
                .orElse(null);

        if(old != null){
            old.setTitle(myEntry.getTitle() != null  &&  !myEntry.getTitle().equals("")    ? myEntry.getTitle() : old.getTitle() );
            old.setContent(myEntry.getContent() != null  &&  !myEntry.getContent().equals("")   ? myEntry.getContent() : old.getContent() );

            journalEntryService.saveEntry( old );
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
