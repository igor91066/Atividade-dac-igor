package com.example.bookatividade.controllers;

import com.example.bookatividade.model.User;
import com.example.bookatividade.model.enums.StatusType;
import com.example.bookatividade.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE - POST
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // READ - GET all
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // READ - GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by login
    @GetMapping("/login/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        Optional<User> user = userService.getUserByLogin(login);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by first name
    @GetMapping("/search/firstName/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        List<User> users = userService.getUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    // READ - GET by last name
    @GetMapping("/search/lastName/{lastName}")
    public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
        List<User> users = userService.getUsersByLastName(lastName);
        return ResponseEntity.ok(users);
    }

    // READ - GET by status
    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<User>> getUsersByStatus(@PathVariable StatusType status) {
        List<User> users = userService.getUsersByStatus(status);
        return ResponseEntity.ok(users);
    }

    // READ - GET active users
    @GetMapping("/search/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH status
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<User> updateUserStatus(@PathVariable Long id, @PathVariable StatusType status) {
        try {
            User updatedUser = userService.updateUserStatus(id, status);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE all
    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    // HELPER - Check if login exists
    @GetMapping("/check-login/{login}")
    public ResponseEntity<Boolean> loginExists(@PathVariable String login) {
        boolean exists = userService.loginExists(login);
        return ResponseEntity.ok(exists);
    }

    // HELPER - Count all users
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        long count = userService.countUsers();
        return ResponseEntity.ok(count);
    }
}
