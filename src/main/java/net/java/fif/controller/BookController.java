package net.java.fif.controller;

import net.java.fif.exception.ResourceNotFoundException;
import net.java.fif.model.Book;
import net.java.fif.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private static final Logger logger = LogManager.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        logger.debug("Showing All Books");
        logger.info("Showing All Books");
        return bookService.getAllBooks();
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Book>> createArrayBook(@RequestBody List<Book> books) {
        List<Book> savedBooks = bookService.createBooks(books);
        return new ResponseEntity<>(savedBooks, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}