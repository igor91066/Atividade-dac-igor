package com.example.bookatividade.services;

import com.example.bookatividade.model.Role;
import com.example.bookatividade.respositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // CREATE
    public Role createRole(Role role) {
        if (roleRepository.existsByRole(role.getRole())) {
            throw new IllegalArgumentException("Role com este nome já existe");
        }
        return roleRepository.save(role);
    }

    // READ - Buscar por ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // READ - Buscar todos
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // READ - Buscar por nome
    public Optional<Role> getRoleByName(String role) {
        return roleRepository.findByRole(role);
    }

    // READ - Buscar roles por palavra-chave
    public List<Role> getRolesByKeyword(String keyword) {
        return roleRepository.findByRoleContainingIgnoreCase(keyword);
    }

    // UPDATE
    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada"));

        if (roleDetails.getRole() != null && !roleDetails.getRole().isEmpty()) {
            if (!roleDetails.getRole().equals(role.getRole()) &&
                roleRepository.existsByRole(roleDetails.getRole())) {
                throw new IllegalArgumentException("Role com este nome já existe");
            }
            role.setRole(roleDetails.getRole());
        }

        return roleRepository.save(role);
    }

    // UPDATE - Atualizar nome da role
    public Role updateRoleName(Long id, String newName) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role não encontrada"));

        if (roleRepository.existsByRole(newName)) {
            throw new IllegalArgumentException("Role com este nome já existe");
        }

        role.setRole(newName);
        return roleRepository.save(role);
    }

    // DELETE
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role não encontrada");
        }
        roleRepository.deleteById(id);
    }

    // DELETE - Deletar todos
    public void deleteAllRoles() {
        roleRepository.deleteAll();
    }

    // HELPER - Verificar se role existe
    public boolean roleExists(String role) {
        return roleRepository.existsByRole(role);
    }

    // HELPER - Contar total de roles
    public long countRoles() {
        return roleRepository.count();
    }
}

