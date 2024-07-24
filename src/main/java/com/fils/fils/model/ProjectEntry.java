package com.fils.fils.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_entries")
public class ProjectEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // No-argument constructor required by JPA
    public ProjectEntry() {
    }

    // Constructor to initialize fields
    public ProjectEntry(String title, String description, String filePath) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
