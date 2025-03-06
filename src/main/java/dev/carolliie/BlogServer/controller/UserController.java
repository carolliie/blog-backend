package dev.carolliie.BlogServer.controller;

import dev.carolliie.BlogServer.entity.User;
import dev.carolliie.BlogServer.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.registerNewUser(user);
        return ResponseEntity.ok(savedUser);
    }*/

    @GetMapping("/{slug}")
    public ResponseEntity<?> getUserBySlug(@PathVariable String slug) {
        try {
            User user = userService.getUserBySlug(slug);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slug not found");
            }

            return ResponseEntity.ok(user);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/edit/{userSlug}")
    public ResponseEntity<?> editUserBySlug(@PathVariable String userSlug, @RequestBody User user) {
        try {
            User editUser = userService.editRegisteredUser(userSlug, user);
            return ResponseEntity.ok("User edited successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
