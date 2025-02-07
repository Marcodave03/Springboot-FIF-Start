package net.java.fif.service;

import net.java.fif.model.Book;
import net.java.fif.model.File;
import net.java.fif.repository.BookRepository;
import net.java.fif.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private FileService fileService;

    private static final String UPLOAD_DIR = "C:/2. Code Repository/100. Repository/FIF/fif/files/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFile_ShouldSaveFileAndReturnFileMetadata() throws IOException {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                "text/plain",
                "Hello, World!".getBytes()
        );

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        File savedFile = new File();
        savedFile.setFile_type(multipartFile.getContentType());
        savedFile.setFile_path(UPLOAD_DIR + multipartFile.getOriginalFilename());
        savedFile.setBook(book);

        when(fileRepository.save(any(File.class))).thenReturn(savedFile);

        // Act
        File result = fileService.addFile(bookId, multipartFile);

        // Assert
        assertNotNull(result);
        assertEquals(multipartFile.getContentType(), result.getFile_type());
        assertEquals(UPLOAD_DIR + multipartFile.getOriginalFilename(), result.getFile_path());
        verify(fileRepository, times(1)).save(any(File.class));
        assertTrue(Files.exists(Paths.get(result.getFile_path())));

        // Clean up the file from the file system after the test
        Files.deleteIfExists(Paths.get(result.getFile_path()));
    }

    @Test
    void addFile_ShouldReturnNullWhenBookNotFound() throws IOException {
        // Arrange
        Long bookId = 1L;
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                "text/plain",
                "Hello, World!".getBytes()
        );

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        File result = fileService.addFile(bookId, multipartFile);

        // Assert
        assertNull(result);
        verify(fileRepository, never()).save(any(File.class));
    }

    @Test
    void downloadFile_ShouldReturnFileBytes() throws IOException {
        // Arrange
        Long fileId = 1L;
        Path filePath = Paths.get(UPLOAD_DIR + "testfile.txt");
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, "Hello, World!".getBytes());

        File file = new File();
        file.setFile_path(filePath.toString());

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        // Act
        byte[] result = fileService.downloadFile(fileId);

        // Assert
        assertNotNull(result);
        assertArrayEquals("Hello, World!".getBytes(), result);
        verify(fileRepository, times(1)).findById(fileId);

        // Clean up the file from the file system after the test
        Files.deleteIfExists(filePath);
    }

    @Test
    void downloadFile_ShouldThrowIOExceptionWhenFileNotFound() {
        // Arrange
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IOException.class, () -> fileService.downloadFile(fileId));
        verify(fileRepository, times(1)).findById(fileId);
    }
}
