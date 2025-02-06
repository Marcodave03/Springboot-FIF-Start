package net.java.fif.service;

import net.java.fif.model.Author;
import net.java.fif.model.Book;
import net.java.fif.repository.AuthorRepository;
import net.java.fif.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author addBookToAuthor(Long authorId, Long bookId){
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalAuthor.isPresent()&&optionalBook.isPresent()){
            Author author = optionalAuthor.get();
            Book book = optionalBook.get();
            book.setAuthor(author);
            author.getBooks().add(book);
            return authorRepository.save(author);
        }
        return null;
    }

    public List<Author> getALlAuthors(){
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(long authorId){
        return authorRepository.findById(authorId);
    }
}
