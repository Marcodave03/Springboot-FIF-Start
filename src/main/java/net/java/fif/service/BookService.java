package net.java.fif.service;

import net.java.fif.controller.BookController;
import net.java.fif.exception.ResourceNotFoundException;
import net.java.fif.model.Book;
import net.java.fif.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.*;

import java.util.List;

@Service
public class BookService {
    private static final Logger logger = LogManager.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        logger.info("Book Created");
        authorService.createAuthor(book.getAuthor());
        return bookRepository.save(book);
    }

    public List<Book> createBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public Book getBookById(long id) {
        logger.info("book with ID found: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public Book updateBook(long id, Book book) {
        Book existingBook = getBookById(id);
        logger.info("Attempting to update book with ID: {}", id);
        existingBook.setTitle(book.getTitle());
        existingBook.setGenre(book.getGenre());
        existingBook.setPrice(book.getPrice());
        logger.info("book with ID updated: {}", id);
        return bookRepository.save(existingBook);

    }

    public void deleteBook(long id) {
        Book book = getBookById(id);
        logger.info("Attempting to delete book with ID: {}", id);
        bookRepository.delete(book);
        logger.info("Book with ID {} deleted successfully", id);
    }
}
