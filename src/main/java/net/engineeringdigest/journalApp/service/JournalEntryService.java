package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        journalEntry.setDate(LocalDateTime.now());
        User usr = userService.findUserbyUsername(username);
        usr.getJournalEntries().add(journalEntry);
        journalEntryRepository.save(journalEntry);
        userService.saveUser(usr);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalbyId(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}