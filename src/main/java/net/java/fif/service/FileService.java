package net.java.fif.service;

import net.java.fif.model.Book;
import net.java.fif.model.File;
import net.java.fif.repository.BookRepository;
import net.java.fif.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BookRepository bookRepository;

    public File addFile(Long bookId, MultipartFile multipartFile) throws IOException {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            // Save the file to the local file system
            String fileName = multipartFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, multipartFile.getBytes());

            // Save file metadata in the database
            File file = new File();
            file.setFile_type(multipartFile.getContentType());
            file.setFile_path(filePath.toString());
            file.setBook(book);

            if (book.getFiles() == null) {
                book.setFiles(new HashSet<>());
            }
            book.getFiles().add(file);

            return fileRepository.save(file);
        }
        return null;
    }

    public byte[] downloadFile(Long fileId) throws IOException {
        Optional<File> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            Path filePath = Paths.get(file.getFile_path());
            return Files.readAllBytes(filePath);
        }
        throw new IOException("File not found");
    }
}
