package com.example.bookatividade.respositories;

import com.example.bookatividade.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Buscar role por nome
    Optional<Role> findByRole(String role);

    // Verificar se role existe
    boolean existsByRole(String role);

    // Buscar roles que contêm uma palavra-chave
    List<Role> findByRoleContainingIgnoreCase(String keyword);
}
