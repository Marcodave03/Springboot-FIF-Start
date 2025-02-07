package net.java.fif.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="author")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="gender", nullable = false)
    private String gender;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JsonManagedReference // ( parent ) -> will be serialized
    private Set<Book> books; //Set -> no duplicate allowed


}
