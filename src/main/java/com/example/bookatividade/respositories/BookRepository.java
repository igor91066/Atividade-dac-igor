package com.example.bookatividade.respositories;

import com.example.bookatividade.model.Book;
import com.example.bookatividade.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar livros por título
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Buscar livros por usuário
    List<Book> findByUser(User user);

    // Buscar livros por ID de usuário
    List<Book> findByUserId(Long userId);

    // Buscar livros por número de páginas
    List<Book> findByPages(Integer pages);

    // Buscar livros com número de páginas maior que
    List<Book> findByPagesGreaterThan(Integer pages);

    // Buscar livros com número de páginas menor que
    List<Book> findByPagesLessThan(Integer pages);

    // Verificar se livro existe por usuário e título
    boolean existsByUserAndTitle(User user, String title);

    // Query customizada para buscar livros por usuário ordenados por título
    @Query("SELECT b FROM Book b WHERE b.user.id = :userId ORDER BY b.title ASC")
    List<Book> findByUserIdOrderByTitle(@Param("userId") Long userId);

    // Query customizada para contar livros por usuário
    @Query("SELECT COUNT(b) FROM Book b WHERE b.user.id = :userId")
    Long countBooksByUserId(@Param("userId") Long userId);
}
