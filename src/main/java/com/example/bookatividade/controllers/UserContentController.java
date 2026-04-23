package com.example.bookatividade.controllers;

import com.example.bookatividade.model.UserContent;
import com.example.bookatividade.services.UserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-contents")
public class UserContentController {

    @Autowired
    private UserContentService userContentService;

    // CREATE - POST
    @PostMapping
    public ResponseEntity<UserContent> createUserContent(@RequestBody UserContent userContent) {
        UserContent createdUserContent = userContentService.createUserContent(userContent);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserContent);
    }

    // READ - GET all
    @GetMapping
    public ResponseEntity<List<UserContent>> getAllUserContents() {
        List<UserContent> userContents = userContentService.getAllUserContents();
        return ResponseEntity.ok(userContents);
    }

    // READ - GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserContent> getUserContentById(@PathVariable Long id) {
        Optional<UserContent> userContent = userContentService.getUserContentById(id);
        return userContent.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by path photo
    @GetMapping("/search/path/{pathPhoto}")
    public ResponseEntity<UserContent> getUserContentByPathPhoto(@PathVariable String pathPhoto) {
        Optional<UserContent> userContent = userContentService.getUserContentByPathPhoto(pathPhoto);
        return userContent.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET all with photo
    @GetMapping("/search/with-photo")
    public ResponseEntity<List<UserContent>> getAllUserContentsWithPhoto() {
        List<UserContent> userContents = userContentService.getAllUserContentsWithPhoto();
        return ResponseEntity.ok(userContents);
    }

    // READ - GET all with path photo
    @GetMapping("/search/with-path-photo")
    public ResponseEntity<List<UserContent>> getAllUserContentsWithPathPhoto() {
        List<UserContent> userContents = userContentService.getAllUserContentsWithPathPhoto();
        return ResponseEntity.ok(userContents);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<UserContent> updateUserContent(@PathVariable Long id, @RequestBody UserContent userContentDetails) {
        try {
            UserContent updatedUserContent = userContentService.updateUserContent(id, userContentDetails);
            return ResponseEntity.ok(updatedUserContent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH photo
    @PatchMapping("/{id}/photo")
    public ResponseEntity<UserContent> updatePhoto(@PathVariable Long id, @RequestBody byte[] photo) {
        try {
            UserContent updatedUserContent = userContentService.updatePhoto(id, photo);
            return ResponseEntity.ok(updatedUserContent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH path photo
    @PatchMapping("/{id}/path/{pathPhoto}")
    public ResponseEntity<UserContent> updatePathPhoto(@PathVariable Long id, @PathVariable String pathPhoto) {
        try {
            UserContent updatedUserContent = userContentService.updatePathPhoto(id, pathPhoto);
            return ResponseEntity.ok(updatedUserContent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserContent(@PathVariable Long id) {
        try {
            userContentService.deleteUserContent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE all
    @DeleteMapping
    public ResponseEntity<Void> deleteAllUserContents() {
        userContentService.deleteAllUserContents();
        return ResponseEntity.noContent().build();
    }

    // HELPER - Check if has photo
    @GetMapping("/has-photo/{id}")
    public ResponseEntity<Boolean> hasPhoto(@PathVariable Long id) {
        boolean hasPhoto = userContentService.hasPhoto(id);
        return ResponseEntity.ok(hasPhoto);
    }

    // HELPER - Count all user contents
    @GetMapping("/count")
    public ResponseEntity<Long> countUserContents() {
        long count = userContentService.countUserContents();
        return ResponseEntity.ok(count);
    }

    // HELPER - Count user contents with photo
    @GetMapping("/count/with-photo")
    public ResponseEntity<Long> countUserContentsWithPhoto() {
        long count = userContentService.countUserContentsWithPhoto();
        return ResponseEntity.ok(count);
    }

    // HELPER - Remove photo
    @DeleteMapping("/{id}/photo")
    public ResponseEntity<UserContent> removePhoto(@PathVariable Long id) {
        try {
            UserContent updatedUserContent = userContentService.removePhoto(id);
            return ResponseEntity.ok(updatedUserContent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // HELPER - Remove path photo
    @DeleteMapping("/{id}/path-photo")
    public ResponseEntity<UserContent> removePathPhoto(@PathVariable Long id) {
        try {
            UserContent updatedUserContent = userContentService.removePathPhoto(id);
            return ResponseEntity.ok(updatedUserContent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
