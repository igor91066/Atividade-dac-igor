package com.example.bookatividade.services;

import com.example.bookatividade.model.Role;
import com.example.bookatividade.model.User;
import com.example.bookatividade.model.enums.StatusType;
import com.example.bookatividade.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE
    public User createUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("Usuário com este login já existe");
        }
        return userRepository.save(user);
    }

    // READ - Buscar por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // READ - Buscar todos
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ - Buscar por login
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    // READ - Buscar por nome
    public List<User> getUsersByFirstName(String firstName) {
        return userRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    // READ - Buscar por sobrenome
    public List<User> getUsersByLastName(String lastName) {
        return userRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    // READ - Buscar por status
    public List<User> getUsersByStatus(StatusType status) {
        return userRepository.findByStatus(status);
    }

    // READ - Buscar usuários ativos
    public List<User> getActiveUsers() {
        return userRepository.findActiveUsers(StatusType.ATIVO);
    }

    // UPDATE
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (userDetails.getFirstName() != null) {
            user.setFirstName(userDetails.getFirstName());
        }
        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getGender() != null) {
            user.setGender(userDetails.getGender());
        }
        if (userDetails.getDateBirth() != null) {
            user.setDateBirth(userDetails.getDateBirth());
        }
        if (userDetails.getStatus() != null) {
            user.setStatus(userDetails.getStatus());
        }
        if (userDetails.getUserContent() != null) {
            user.setUserContent(userDetails.getUserContent());
        }

        return userRepository.save(user);
    }

    // UPDATE - Atualizar status
    public User updateUserStatus(Long id, StatusType status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        user.setStatus(status);
        return userRepository.save(user);
    }

    // DELETE
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }

    // DELETE - Deletar todos
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // HELPER - Adicionar role ao usuário
    public User addRoleToUser(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    // HELPER - Remover role do usuário
    public User removeRoleFromUser(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    // HELPER - Verificar se login já existe
    public boolean loginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    // HELPER - Contar total de usuários
    public long countUsers() {
        return userRepository.count();
    }
}
