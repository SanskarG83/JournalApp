package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<JournalEntry>> getAllJournals(){
        return new ResponseEntity<>(journalEntryService.getAllEntries(), HttpStatus.OK);
    }

    @GetMapping("/getAll/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalOfUser(@PathVariable String username){
        User usr = userService.findUserbyUsername(username);
        List<JournalEntry> all = usr.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{entryId}")
    public ResponseEntity<JournalEntry> getEntrybyId(@PathVariable ObjectId entryId){
        return new ResponseEntity<>(journalEntryService.getJournalbyId(entryId).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/create-new/{username}")
    public ResponseEntity<String> createJournal(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>("Entry Created", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Exception occured: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{entryId}/{username}")
    public ResponseEntity<String> updateEntry(@PathVariable ObjectId entryId, @RequestBody JournalEntry newEntry, @RequestBody String username){
        JournalEntry oldEntry = journalEntryService.getJournalbyId(entryId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : oldEntry.getDescription());
        }
        journalEntryService.saveEntry(oldEntry, username);
        return new ResponseEntity<>("Entry Updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{entryId}/{username}")
    public ResponseEntity<String> deleteEntrybyId(@PathVariable ObjectId entryId, @PathVariable String username){
        User usr = userService.findUserbyUsername(username);
        List<JournalEntry> userJournals = usr.getJournalEntries();
        for(JournalEntry entry: userJournals){
            if(entry.getId().equals(entryId)){
                userJournals.remove(entry);
            }
            else{
                return new ResponseEntity<>("Entry does not exist", HttpStatus.NOT_FOUND);
            }
        }
        userService.saveUser(usr);
        journalEntryService.deleteEntry(entryId);
        return new ResponseEntity<>("Entry deleted", HttpStatus.NOT_FOUND);
    }

}
