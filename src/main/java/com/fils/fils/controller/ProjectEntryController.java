package com.fils.fils.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fils.fils.model.ProjectEntry;
import com.fils.fils.service.ProjectEntryService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/entries")
public class ProjectEntryController {

    private static final Logger logger = Logger.getLogger(ProjectEntryController.class.getName());

    private final ProjectEntryService service;

    @Autowired
    public ProjectEntryController(ProjectEntryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProjectEntry> createEntry(@RequestParam String title,
                                                    @RequestParam String description,
                                                    @RequestParam(required = false) MultipartFile file) {
        try {
            logger.info("Received request to create entry: title=" + title + ", description=" + description);
            ProjectEntry entry = new ProjectEntry(title, description, null);
            ProjectEntry savedEntry = service.saveEntry(entry, file);
            return ResponseEntity.ok(savedEntry);
        } catch (Exception e) {
            logger.severe("Error creating entry: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public List<ProjectEntry> getAllEntries() {
        return service.getAllEntries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntry> getEntryById(@PathVariable Long id) {
        return service.getEntryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

        @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ProjectEntry> entryOpt = service.getEntryById(id);
        if (entryOpt.isPresent()) {
            ProjectEntry entry = entryOpt.get();
            try {
                Path filePath = Paths.get(entry.getFilePath()).normalize();
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectEntry> updateEntry(@PathVariable Long id, @RequestBody ProjectEntry updatedEntry) {
        return service.getEntryById(id).map(entry -> {
            entry.setTitle(updatedEntry.getTitle());
            entry.setDescription(updatedEntry.getDescription());
            ProjectEntry savedEntry = service.saveEntry(entry, null);
            return ResponseEntity.ok(savedEntry);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

}
