package dev.harshit.filestorage.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private LocalDateTime uploadTime;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FileVersion> versions = new ArrayList<>();

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FileShare> shares = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public List<FileVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<FileVersion> versions) {
        this.versions = versions;
    }

    public List<FileShare> getShares() {
        return shares;
    }

    public void setShares(List<FileShare> shares) {
        this.shares = shares;
    }
}
