//package net.java.fif.controller;
//
//import net.java.fif.model.Author;
//import net.java.fif.service.AuthorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.*;
//
//import java.util.*;
//
//import static org.mockito.Mockito.*;
//
//class AuthorControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private AuthorService authorService;
//
//    @InjectMocks
//    private AuthorController authorController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
//    }
//
//    @Test
//    void testCreateAuthor() throws Exception {
//        Author author = new Author();
//        author.setId(1L);
//        author.setName("John Doe");
//
//        when(authorService.createAuthor(any(Author.class))).thenReturn(author);
//
//        mockMvc.perform(post("/api/v1/authors/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"John Doe\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("John Doe"));
//
//        verify(authorService, times(1)).createAuthor(any(Author.class));
//    }
//
//    @Test
//    void testAddBookToAuthor() throws Exception {
//        Author author = new Author();
//        author.setId(1L);
//        author.setName("John Doe");
//
//        when(authorService.addBookToAuthor(1L, 1L)).thenReturn(author);
//
//        mockMvc.perform(post("/api/v1/authors/1/books/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("John Doe"));
//
//        verify(authorService, times(1)).addBookToAuthor(1L, 1L);
//    }
//
//    @Test
//    void testGetAllAuthors() throws Exception {
//        Author author1 = new Author();
//        author1.setId(1L);
//        author1.setName("John Doe");
//
//        Author author2 = new Author();
//        author2.setId(2L);
//        author2.setName("Jane Smith");
//
//        when(authorService.getALlAuthors()).thenReturn(Arrays.asList(author1, author2));
//
//        mockMvc.perform(get("/api/v1/authors/get"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].name").value("John Doe"))
//                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
//
//        verify(authorService, times(1)).getALlAuthors();
//    }
//
//    @Test
//    void testGetAuthorById() throws Exception {
//        Author author = new Author();
//        author.setId(1L);
//        author.setName("John Doe");
//
//        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
//
//        mockMvc.perform(get("/api/v1/authors/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("John Doe"));
//
//        verify(authorService, times(1)).getAuthorById(1L);
//    }
//
//    @Test
//    void testGetAuthorById_NotFound() throws Exception {
//        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/v1/authors/1"))
//                .andExpect(status().isNotFound());
//
//        verify(authorService, times(1)).getAuthorById(1L);
//    }
//}
