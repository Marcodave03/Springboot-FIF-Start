package net.java.fif;

import net.java.fif.repository.BookRepository;
import net.java.fif.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FifApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FifApplication.class, args);
	}

	@Autowired
	private BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception {

	}
}
