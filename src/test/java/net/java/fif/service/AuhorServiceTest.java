package net.java.fif.service;

import net.java.fif.model.*;
import net.java.fif.repository.*;
import net.java.fif.service.AuthorService;
import net.java.fif.exception.ResourceNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuhorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
        author.setGender("Male");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
    }

    @Test
    void testCreateAuthor(){
        //Arrange
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        //Act
        Author createdAuthor = authorService.createAuthor(author);

        assertNotNull(createdAuthor);
        assertEquals("Test Author", createdAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testAddBookToAuthor(){
        //Arrange
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        //Act
        Author updatedAuthor = authorService.addBookToAuthor(1L,1L);


        //Assertion
        assertNotNull(updatedAuthor);
        assertEquals(1,updatedAuthor.getBooks().size());
        assertEquals(author,book.getAuthor());
        verify(authorRepository,times(1)).save(author);
    }

    @Test
    void testGetAllAuthors(){
        //Arrange
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);

        //Act
        List<Author> result = authorService.getALlAuthors();

        //Assertion
        assertNotNull(result);
        assertEquals(1,result.size());
        verify(authorRepository,times(1)).findAll();
    }

    @Test
    void testGetAuthorById(){
        //Arrange
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        //Act
        Optional<Author> foundAuthor = authorService.getAuthorById(1L);

        assertTrue(foundAuthor.isPresent());
        assertEquals("Test Author", foundAuthor.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }
}

