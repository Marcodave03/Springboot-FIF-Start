package net.java.fif.controller;

import net.java.fif.model.File;
import net.java.fif.service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private static final Logger logger = LogManager.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/{bookId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long bookId, @RequestParam("file") MultipartFile file) {
        try {
            File savedFile = fileService.addFile(bookId, file);
            return new ResponseEntity<>("File uploaded successfully with ID: " + savedFile.getId(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        try {
            byte[] fileData = fileService.downloadFile(fileId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "downloaded_file");

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
