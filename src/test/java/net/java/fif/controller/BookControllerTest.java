//package net.java.fif.controller;
//
//import net.java.fif.model.Author;
//import net.java.fif.model.Book;
//import net.java.fif.service.BookService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BookControllerTest {
//
//    @Mock
//    private BookService bookService;
//
//    @InjectMocks
//    private BookController bookController;
//
//    private Book book1;
//    private Book book2;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        //1L, "Book One", "Author One", "1234567890"
//        book1 = new Book();
//        book1.setId("1L");
//
//
//        book2 = new Book(2L, "Book Two", "Author Two", "0987654321");
//
//    }
//
//    @Test
//    void testGetAllBooks() {
//        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));
//
//        List<Book> books = bookController.getAllBooks();
//
//        assertEquals(2, books.size());
//        verify(bookService, times(1)).getAllBooks();
//    }
//
//    @Test
//    void testCreateBook() {
//        when(bookService.createBook(any(Book.class))).thenReturn(book1);
//
//        ResponseEntity<Book> response = bookController.createBook(book1);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(book1, response.getBody());
//        verify(bookService, times(1)).createBook(book1);
//    }
//
//    @Test
//    void testCreateArrayBook() {
//        List<Book> booksToCreate = Arrays.asList(book1, book2);
//        when(bookService.createBooks(booksToCreate)).thenReturn(booksToCreate);
//
//        ResponseEntity<List<Book>> response = bookController.createArrayBook(booksToCreate);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(2, response.getBody().size());
//        verify(bookService, times(1)).createBooks(booksToCreate);
//    }
//
//    @Test
//    void testGetBookById() {
//        when(bookService.getBookById(1L)).thenReturn(book1);
//
//        ResponseEntity<Book> response = bookController.getBookById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(book1, response.getBody());
//        verify(bookService, times(1)).getBookById(1L);
//    }
//}
