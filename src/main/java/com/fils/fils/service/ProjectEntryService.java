package com.fils.fils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.fils.fils.model.ProjectEntry;
import com.fils.fils.repository.ProjectEntryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.UUID;

@Service
@Transactional
public class ProjectEntryService {

    private static final Logger logger = Logger.getLogger(ProjectEntryService.class.getName());

    private final ProjectEntryRepository repository;
    private final Path fileStorageLocation;

    @Autowired
    public ProjectEntryService(ProjectEntryRepository repository) {
        this.repository = repository;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public ProjectEntry saveEntry(ProjectEntry entry, MultipartFile file) {
        logger.info("Saving entry: " + entry);
        if (file != null && !file.isEmpty()) {
            String fileName = storeFile(file);
            entry.setFilePath(fileName);
        }
        ProjectEntry savedEntry = repository.save(entry);
        logger.info("Saved entry: " + savedEntry);
        return savedEntry;
    }

    private String storeFile(MultipartFile file) {
        try {
            // Generate a unique file name using UUID
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }

    public List<ProjectEntry> getAllEntries() {
        return repository.findAll();
    }

    public Optional<ProjectEntry> getEntryById(Long id) {
        return repository.findById(id);
    }

    // Add this method to your service

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }


}
