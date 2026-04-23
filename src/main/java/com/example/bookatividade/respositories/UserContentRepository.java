package com.example.bookatividade.respositories;

import com.example.bookatividade.model.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserContentRepository extends JpaRepository<UserContent, Long> {

    // Buscar por caminho da foto
    Optional<UserContent> findByPathPhoto(String pathPhoto);

    // Buscar conteúdos que possuem foto
    @Query("SELECT uc FROM UserContent uc WHERE uc.photo IS NOT NULL")
    List<UserContent> findAllWithPhoto();

    // Buscar conteúdos que possuem caminho de foto
    @Query("SELECT uc FROM UserContent uc WHERE uc.pathPhoto IS NOT NULL")
    List<UserContent> findAllWithPathPhoto();

    // Verificar se existe foto
    @Query("SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END FROM UserContent uc WHERE uc.id = :id AND uc.photo IS NOT NULL")
    boolean hasPhoto(@Param("id") Long id);
}
