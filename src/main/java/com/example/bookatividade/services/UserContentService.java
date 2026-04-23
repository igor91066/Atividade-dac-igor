package com.example.bookatividade.services;

import com.example.bookatividade.model.UserContent;
import com.example.bookatividade.respositories.UserContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserContentService {

    @Autowired
    private UserContentRepository userContentRepository;

    // CREATE
    public UserContent createUserContent(UserContent userContent) {
        return userContentRepository.save(userContent);
    }

    // READ - Buscar por ID
    public Optional<UserContent> getUserContentById(Long id) {
        return userContentRepository.findById(id);
    }

    // READ - Buscar todos
    public List<UserContent> getAllUserContents() {
        return userContentRepository.findAll();
    }

    // READ - Buscar por caminho de foto
    public Optional<UserContent> getUserContentByPathPhoto(String pathPhoto) {
        return userContentRepository.findByPathPhoto(pathPhoto);
    }

    // READ - Buscar todos com foto
    public List<UserContent> getAllUserContentsWithPhoto() {
        return userContentRepository.findAllWithPhoto();
    }

    // READ - Buscar todos com caminho de foto
    public List<UserContent> getAllUserContentsWithPathPhoto() {
        return userContentRepository.findAllWithPathPhoto();
    }

    // UPDATE
    public UserContent updateUserContent(Long id, UserContent userContentDetails) {
        UserContent userContent = userContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conteúdo de usuário não encontrado"));

        if (userContentDetails.getPhoto() != null) {
            userContent.setPhoto(userContentDetails.getPhoto());
        }
        if (userContentDetails.getPathPhoto() != null) {
            userContent.setPathPhoto(userContentDetails.getPathPhoto());
        }

        return userContentRepository.save(userContent);
    }

    // UPDATE - Atualizar foto
    public UserContent updatePhoto(Long id, byte[] photo) {
        UserContent userContent = userContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conteúdo de usuário não encontrado"));
        userContent.setPhoto(photo);
        return userContentRepository.save(userContent);
    }

    // UPDATE - Atualizar caminho da foto
    public UserContent updatePathPhoto(Long id, String pathPhoto) {
        UserContent userContent = userContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conteúdo de usuário não encontrado"));
        userContent.setPathPhoto(pathPhoto);
        return userContentRepository.save(userContent);
    }

    // DELETE
    public void deleteUserContent(Long id) {
        if (!userContentRepository.existsById(id)) {
            throw new IllegalArgumentException("Conteúdo de usuário não encontrado");
        }
        userContentRepository.deleteById(id);
    }

    // DELETE - Deletar todos
    public void deleteAllUserContents() {
        userContentRepository.deleteAll();
    }

    // HELPER - Verificar se tem foto
    public boolean hasPhoto(Long id) {
        return userContentRepository.hasPhoto(id);
    }

    // HELPER - Contar total de conteúdos
    public long countUserContents() {
        return userContentRepository.count();
    }

    // HELPER - Contar conteúdos com foto
    public long countUserContentsWithPhoto() {
        return getAllUserContentsWithPhoto().size();
    }

    // HELPER - Remover foto
    public UserContent removePhoto(Long id) {
        UserContent userContent = userContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conteúdo de usuário não encontrado"));
        userContent.setPhoto(null);
        return userContentRepository.save(userContent);
    }

    // HELPER - Remover caminho da foto
    public UserContent removePathPhoto(Long id) {
        UserContent userContent = userContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conteúdo de usuário não encontrado"));
        userContent.setPathPhoto(null);
        return userContentRepository.save(userContent);
    }
}

