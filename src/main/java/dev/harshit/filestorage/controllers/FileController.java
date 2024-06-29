package dev.harshit.filestorage.controllers;

import dev.harshit.filestorage.models.File;
import dev.harshit.filestorage.models.FileShare;
import dev.harshit.filestorage.models.FileVersion;
import dev.harshit.filestorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File storedFile = fileService.storeFile(file);
            return new ResponseEntity<>(storedFile, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileId}/{version}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, @PathVariable String version) {
        try {
            FileVersion fileVersion = fileService.getFileVersion(fileId, version);
            Resource resource = fileService.loadFileAsResource(fileVersion.getPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/share")
    public ResponseEntity<FileShare> shareFile(@RequestParam Long fileId, @RequestParam String sharedWith) {
        try {
            FileShare fileShare = fileService.shareFile(fileId, sharedWith);
            return new ResponseEntity<>(fileShare, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shared/{sharedWith}")
    public ResponseEntity<List<FileShare>> getSharedFiles(@PathVariable String sharedWith) {
        try {
            List<FileShare> sharedFiles = fileService.getSharedFiles(sharedWith);
            return new ResponseEntity<>(sharedFiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
