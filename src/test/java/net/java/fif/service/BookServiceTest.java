package net.java.fif.service;

import net.java.fif.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import net.java.fif.model.Book;
import net.java.fif.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    private Book book;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setGenre("Fiction");
        book.setPrice((int) 13.33);
    }

    @Test
    void testgetAllBooks() {
        //Arange
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);

        //Act
        List<Book> result = bookService.getAllBooks();

        //Assert
        assertEquals(1,result.size());
        assertEquals("Test Book",result.get(0).getTitle());
        verify(bookRepository,times(1)).findAll();
    }

    @Test
    void testCreateBook() {
        //Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        //Act
        Book createdBook = bookService.createBook(book);

        //Assert
        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
        verify(authorService,times(1)).createAuthor(book.getAuthor());
        verify(bookRepository,times(1)).save(book);
    }


    @Test
    void testGetBookById_Found() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book foundBook = bookService.getBookById(1L);

        // Assert
        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        // Arrange
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(2L));
        verify(bookRepository, times(1)).findById(2L);
    }

    //update
    //delete
}