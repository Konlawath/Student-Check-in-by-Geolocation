package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import java.util.Optional;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    
    
 // PATCH: update email ของ user ตาม firstName + lastName
    @PatchMapping("/update-email")
    public ResponseEntity<String> updateEmail(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String newEmail) {

        Optional<User> userOpt = userRepository.findByFirstNameAndLastName(firstName, lastName);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(newEmail);
            userRepository.save(user);
            return ResponseEntity.ok("Email updated successfully for user: " + firstName + " " + lastName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
