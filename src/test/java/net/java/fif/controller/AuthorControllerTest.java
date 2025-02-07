package net.java.fif.controller;

import net.java.fif.model.Author;
import net.java.fif.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author1 = new Author();
        author1.setId(1L);
        author1.setName("Author One");

        author2 = new Author();
        author2.setId(2L);
        author2.setName("Author Two");
    }

    @Test
    void testCreateAuthor() {
        when(authorService.createAuthor(any(Author.class))).thenReturn(author1);

        ResponseEntity<Author> response = authorController.createAuthor(author1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author1, response.getBody());
        verify(authorService, times(1)).createAuthor(author1);
    }

    @Test
    void testAddBookToAuthor() {
        when(authorService.addBookToAuthor(1L, 1L)).thenReturn(author1);

        ResponseEntity<Author> response = authorController.addBookToAuthor(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author1, response.getBody());
        verify(authorService, times(1)).addBookToAuthor(1L, 1L);
    }

    @Test
    void testAddBookToAuthor_NotFound() {
        when(authorService.addBookToAuthor(1L, 1L)).thenReturn(null);

        ResponseEntity<Author> response = authorController.addBookToAuthor(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorService, times(1)).addBookToAuthor(1L, 1L);
    }

    @Test
    void testGetAllAuthors() {
        when(authorService.getALlAuthors()).thenReturn(Arrays.asList(author1, author2));

        ResponseEntity<List<Author>> response = authorController.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(authorService, times(1)).getALlAuthors();
    }

    @Test
    void testGetAuthorById() {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author1));

        ResponseEntity<Author> response = authorController.getAuthorById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author1, response.getBody());
        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Author> response = authorController.getAuthorById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorService, times(1)).getAuthorById(1L);
    }
}