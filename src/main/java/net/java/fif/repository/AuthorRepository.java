package net.java.fif.repository;

import net.java.fif.model.Author;
import  org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository <Author,Long> {
}
