package net.engineeringdigest.journalApp.service;

import java.util.*;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findUserbyUsername(String username){
        return userRepository.findByusername(username);
    }

    public void deleteUserbyId(ObjectId id){
        userRepository.deleteById(id);
    }

}
