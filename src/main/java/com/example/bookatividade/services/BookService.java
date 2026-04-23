package com.example.bookatividade.services;

import com.example.bookatividade.model.Book;
import com.example.bookatividade.model.User;
import com.example.bookatividade.respositories.BookRepository;
import com.example.bookatividade.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE
    public Book createBook(Book book) {
        if (book.getUser() == null || book.getUser().getId() == null) {
            throw new IllegalArgumentException("Livro deve estar associado a um usuário");
        }
        User user = userRepository.findById(book.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        book.setUser(user);
        return bookRepository.save(book);
    }

    // READ - Buscar por ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // READ - Buscar todos
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // READ - Buscar por título
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // READ - Buscar livros por usuário
    public List<Book> getBooksByUser(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    // READ - Buscar livros por usuário ordenado por título
    public List<Book> getBooksByUserOrderedByTitle(Long userId) {
        return bookRepository.findByUserIdOrderByTitle(userId);
    }

    // READ - Buscar livros por número de páginas
    public List<Book> getBooksByPages(Integer pages) {
        return bookRepository.findByPages(pages);
    }

    // READ - Buscar livros com mais páginas que
    public List<Book> getBooksWithMorePages(Integer pages) {
        return bookRepository.findByPagesGreaterThan(pages);
    }

    // READ - Buscar livros com menos páginas que
    public List<Book> getBooksWithLessPages(Integer pages) {
        return bookRepository.findByPagesLessThan(pages);
    }

    // UPDATE
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        if (bookDetails.getTitle() != null) {
            book.setTitle(bookDetails.getTitle());
        }
        if (bookDetails.getPages() != null) {
            book.setPages(bookDetails.getPages());
        }

        return bookRepository.save(book);
    }

    // UPDATE - Atualizar título
    public Book updateBookTitle(Long id, String title) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
        book.setTitle(title);
        return bookRepository.save(book);
    }

    // UPDATE - Atualizar número de páginas
    public Book updateBookPages(Long id, Integer pages) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
        book.setPages(pages);
        return bookRepository.save(book);
    }

    // DELETE
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado");
        }
        bookRepository.deleteById(id);
    }

    // DELETE - Deletar todos
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    // DELETE - Deletar livros por usuário
    public void deleteBooksByUser(Long userId) {
        List<Book> books = bookRepository.findByUserId(userId);
        bookRepository.deleteAll(books);
    }

    // HELPER - Contar livros por usuário
    public Long countBooksByUser(Long userId) {
        return bookRepository.countBooksByUserId(userId);
    }

    // HELPER - Verificar if livro existe por usuário e título
    public boolean bookExists(Long userId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return bookRepository.existsByUserAndTitle(user, title);
    }

    // HELPER - Contar total de livros
    public long countBooks() {
        return bookRepository.count();
    }
}

