package net.engineeringdigest.journalApp.controller;

import java.util.*;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>("User Created", HttpStatus.OK);
    }

    @PutMapping("/updateUser/{username}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable String username){
        User currUser = userService.findUserbyUsername(username);
        System.out.println("current user : "+ currUser);
        if(currUser!=null){
            currUser.setUsername(user.getUsername());
            currUser.setPassword(user.getPassword());
            userService.saveUser(currUser);
        }

        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }

}
