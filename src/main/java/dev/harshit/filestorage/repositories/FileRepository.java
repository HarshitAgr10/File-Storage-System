package dev.harshit.filestorage.repositories;

import dev.harshit.filestorage.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
