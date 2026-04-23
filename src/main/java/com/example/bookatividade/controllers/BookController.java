package com.example.bookatividade.controllers;

import com.example.bookatividade.model.Book;
import com.example.bookatividade.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // CREATE - POST
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book createdBook = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // READ - GET all
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // READ - GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by title
    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
        List<Book> books = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    // READ - GET books by user
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUser(@PathVariable Long userId) {
        List<Book> books = bookService.getBooksByUser(userId);
        return ResponseEntity.ok(books);
    }

    // READ - GET books by user ordered by title
    @GetMapping("/search/user/{userId}/ordered")
    public ResponseEntity<List<Book>> getBooksByUserOrderedByTitle(@PathVariable Long userId) {
        List<Book> books = bookService.getBooksByUserOrderedByTitle(userId);
        return ResponseEntity.ok(books);
    }

    // READ - GET by pages
    @GetMapping("/search/pages/{pages}")
    public ResponseEntity<List<Book>> getBooksByPages(@PathVariable Integer pages) {
        List<Book> books = bookService.getBooksByPages(pages);
        return ResponseEntity.ok(books);
    }

    // READ - GET books with more pages than
    @GetMapping("/search/pages/more/{pages}")
    public ResponseEntity<List<Book>> getBooksWithMorePages(@PathVariable Integer pages) {
        List<Book> books = bookService.getBooksWithMorePages(pages);
        return ResponseEntity.ok(books);
    }

    // READ - GET books with less pages than
    @GetMapping("/search/pages/less/{pages}")
    public ResponseEntity<List<Book>> getBooksWithLessPages(@PathVariable Integer pages) {
        List<Book> books = bookService.getBooksWithLessPages(pages);
        return ResponseEntity.ok(books);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH title
    @PatchMapping("/{id}/title/{title}")
    public ResponseEntity<Book> updateBookTitle(@PathVariable Long id, @PathVariable String title) {
        try {
            Book updatedBook = bookService.updateBookTitle(id, title);
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH pages
    @PatchMapping("/{id}/pages/{pages}")
    public ResponseEntity<Book> updateBookPages(@PathVariable Long id, @PathVariable Integer pages) {
        try {
            Book updatedBook = bookService.updateBookPages(id, pages);
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE all
    @DeleteMapping
    public ResponseEntity<Void> deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }

    // DELETE books by user
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteBooksByUser(@PathVariable Long userId) {
        bookService.deleteBooksByUser(userId);
        return ResponseEntity.noContent().build();
    }

    // HELPER - Count books by user
    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Long> countBooksByUser(@PathVariable Long userId) {
        Long count = bookService.countBooksByUser(userId);
        return ResponseEntity.ok(count);
    }

    // HELPER - Check if book exists
    @GetMapping("/exists/user/{userId}/title/{title}")
    public ResponseEntity<Boolean> bookExists(@PathVariable Long userId, @PathVariable String title) {
        boolean exists = bookService.bookExists(userId, title);
        return ResponseEntity.ok(exists);
    }

    // HELPER - Count all books
    @GetMapping("/count")
    public ResponseEntity<Long> countBooks() {
        long count = bookService.countBooks();
        return ResponseEntity.ok(count);
    }
}
