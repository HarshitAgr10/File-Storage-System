package dev.harshit.filestorage.repositories;

import dev.harshit.filestorage.models.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    List<FileShare> findBySharedWith(String sharedWith);
}
