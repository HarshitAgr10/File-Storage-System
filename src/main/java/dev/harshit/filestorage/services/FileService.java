package dev.harshit.filestorage.services;

import dev.harshit.filestorage.models.File;
import dev.harshit.filestorage.models.FileShare;
import dev.harshit.filestorage.models.FileVersion;
import dev.harshit.filestorage.repositories.FileRepository;
import dev.harshit.filestorage.repositories.FileShareRepository;
import dev.harshit.filestorage.repositories.FileVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileVersionRepository fileVersionRepository;

    @Autowired
    private FileShareRepository fileShareRepository;

    private final String storageDir = "storage/";

    public File storeFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        Path filePath = Paths.get(storageDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        File file = new File();
        file.setName(fileName);
        file.setPath(filePath.toString());
        file.setUploadTime(LocalDateTime.now());

        fileRepository.save(file);

        FileVersion version = new FileVersion();
        version.setVersionName("v1");
        version.setPath(filePath.toString());
        version.setUploadTime(LocalDateTime.now());
        version.setFile(file);

        fileVersionRepository.save(version);

        return file;
    }

    public FileVersion getFileVersion(Long fileId, String versionName) {
        return fileVersionRepository.findByFileIdAndVersionName(fileId, versionName);
    }

    public Resource loadFileAsResource(String filePath) throws MalformedURLException {
        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found " + filePath);
        }
    }

    public FileShare shareFile(Long fileId, String sharedWith) {
        Optional<File> fileOpt = fileRepository.findById(fileId);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            FileShare fileShare = new FileShare();
            fileShare.setSharedWith(sharedWith);
            fileShare.setFile(file);
            fileShare.setSharedAt(LocalDateTime.now());
            return fileShareRepository.save(fileShare);
        }
        throw new RuntimeException("File not found ");
    }

    public List<FileShare> getSharedFiles(String sharedWith) {
        return fileShareRepository.findBySharedWith(sharedWith);
    }
}
