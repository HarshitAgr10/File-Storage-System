package dev.harshit.filestorage.repositories;

import dev.harshit.filestorage.models.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileVersionRepository extends JpaRepository<FileVersion, Long> {
    FileVersion findByFileIdAndVersionName(Long fileId, String versionName);
}
