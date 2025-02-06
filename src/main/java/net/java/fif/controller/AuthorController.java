package net.java.fif.controller;
import net.java.fif.model.Author;
import net.java.fif.service.AuthorService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }

    //add book using path variable
    @PostMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Author> addBookToAuthor (@PathVariable Long authorId, @PathVariable Long bookId){
        Author updatedAuthor = authorService.addBookToAuthor(authorId,bookId);
        if(updatedAuthor!=null){
            return ResponseEntity.ok(updatedAuthor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<Author>> getAllAuthors(){
        //return authorService.getALlAuthors();
        return ResponseEntity.ok(authorService.getALlAuthors());
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable long authorId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        //return author.orElse(null);
        return author.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
