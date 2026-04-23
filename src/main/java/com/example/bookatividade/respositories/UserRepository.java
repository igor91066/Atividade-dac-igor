package com.example.bookatividade.respositories;

import com.example.bookatividade.model.User;
import com.example.bookatividade.model.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar por login/username
    Optional<User> findByLogin(String login);

    // Buscar por nome
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    // Buscar por sobrenome
    List<User> findByLastNameContainingIgnoreCase(String lastName);

    // Buscar por status
    List<User> findByStatus(StatusType status);

    // Buscar por email/login e status
    Optional<User> findByLoginAndStatus(String login, StatusType status);

    // Verificar se login já existe
    boolean existsByLogin(String login);

    // Query customizada para buscar múltiplos usuários por status
    @Query("SELECT u FROM User u WHERE u.status = :status ORDER BY u.firstName ASC")
    List<User> findActiveUsers(@Param("status") StatusType status);
}
