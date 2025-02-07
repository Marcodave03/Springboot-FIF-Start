package net.java.fif.controller;

import net.java.fif.model.File;
import net.java.fif.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFile() throws IOException {
        Long bookId = 1L;
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());

        File savedFile = new File();
        savedFile.setId(1L);

        when(fileService.addFile(eq(bookId), any(MultipartFile.class))).thenReturn(savedFile);

        ResponseEntity<String> response = fileController.uploadFile(bookId, multipartFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("File uploaded successfully with ID: 1"));
        verify(fileService, times(1)).addFile(bookId, multipartFile);
    }

    @Test
    void testUploadFileIOException() throws IOException {
        Long bookId = 1L;
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());

        when(fileService.addFile(eq(bookId), any(MultipartFile.class))).thenThrow(new IOException("Failed to save file"));

        ResponseEntity<String> response = fileController.uploadFile(bookId, multipartFile);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("File upload failed: Failed to save file"));
        verify(fileService, times(1)).addFile(bookId, multipartFile);
    }

    @Test
    void testDownloadFile() throws IOException {
        Long fileId = 1L;
        byte[] fileData = "Test content".getBytes();

        when(fileService.downloadFile(fileId)).thenReturn(fileData);

        ResponseEntity<byte[]> response = fileController.downloadFile(fileId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(fileData, response.getBody());
        assertEquals("attachment; filename=\"downloaded_file\"", response.getHeaders().getFirst("Content-Disposition"));
        verify(fileService, times(1)).downloadFile(fileId);
    }

    @Test
    void testDownloadFileIOException() throws IOException {
        Long fileId = 1L;

        when(fileService.downloadFile(fileId)).thenThrow(new IOException("File not found"));

        ResponseEntity<byte[]> response = fileController.downloadFile(fileId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fileService, times(1)).downloadFile(fileId);
    }
}