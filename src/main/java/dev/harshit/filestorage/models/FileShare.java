package dev.harshit.filestorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FileShare {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String sharedWith;
        private LocalDateTime sharedAt;

        @ManyToOne
        @JoinColumn(name = "file_id")
        @JsonBackReference
        private File file;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getSharedWith() {
                return sharedWith;
        }

        public void setSharedWith(String sharedWith) {
                this.sharedWith = sharedWith;
        }

        public LocalDateTime getSharedAt() {
                return sharedAt;
        }

        public void setSharedAt(LocalDateTime sharedAt) {
                this.sharedAt = sharedAt;
        }

        public File getFile() {
                return file;
        }

        public void setFile(File file) {
                this.file = file;
        }
}
