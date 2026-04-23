package com.example.bookatividade.controllers;

import com.example.bookatividade.model.Role;
import com.example.bookatividade.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // CREATE - POST
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.createRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // READ - GET all
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // READ - GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by name
    @GetMapping("/search/name/{role}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String role) {
        Optional<Role> foundRole = roleService.getRoleByName(role);
        return foundRole.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by keyword
    @GetMapping("/search/keyword/{keyword}")
    public ResponseEntity<List<Role>> getRolesByKeyword(@PathVariable String keyword) {
        List<Role> roles = roleService.getRolesByKeyword(keyword);
        return ResponseEntity.ok(roles);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        try {
            Role updatedRole = roleService.updateRole(id, roleDetails);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // UPDATE - PATCH name
    @PatchMapping("/{id}/name/{newName}")
    public ResponseEntity<Role> updateRoleName(@PathVariable Long id, @PathVariable String newName) {
        try {
            Role updatedRole = roleService.updateRoleName(id, newName);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE all
    @DeleteMapping
    public ResponseEntity<Void> deleteAllRoles() {
        roleService.deleteAllRoles();
        return ResponseEntity.noContent().build();
    }

    // HELPER - Check if role exists
    @GetMapping("/check-exists/{role}")
    public ResponseEntity<Boolean> roleExists(@PathVariable String role) {
        boolean exists = roleService.roleExists(role);
        return ResponseEntity.ok(exists);
    }

    // HELPER - Count all roles
    @GetMapping("/count")
    public ResponseEntity<Long> countRoles() {
        long count = roleService.countRoles();
        return ResponseEntity.ok(count);
    }
}
