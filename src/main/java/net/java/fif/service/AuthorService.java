package net.java.fif.service;

import net.java.fif.model.Author;
import net.java.fif.model.Book;
import net.java.fif.repository.AuthorRepository;
import net.java.fif.repository.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthorService {
    private static final Logger logger = LogManager.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Author createAuthor(Author author){
        logger.info("Author Created");
        return authorRepository.save(author);
    }

    public Author addBookToAuthor(Long authorId, Long bookId){
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalAuthor.isPresent()&&optionalBook.isPresent()){
            Author author = optionalAuthor.get();
            Book book = optionalBook.get();
            book.setAuthor(author);
            if (author.getBooks() == null) {
                logger.warn("Books set is null for author with ID: {}. Initializing a new set.", authorId);
                author.setBooks(new HashSet<>());
            }
            author.getBooks().add(book);
            logger.info("Book Added");
            return authorRepository.save(author);
        }
        return null;
    }

    public List<Author> getALlAuthors(){
        logger.info("Show all author");
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(long authorId){
        logger.info("Getting Author ID : {}",authorId);
        return authorRepository.findById(authorId);
    }
}
