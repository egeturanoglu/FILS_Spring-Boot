package com.fils.fils.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fils.fils.model.Presentation;
import com.fils.fils.service.PresentationService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/presentations")
@CrossOrigin(origins = "http://localhost:3000")
public class PresentationController {
    @Autowired
    private PresentationService presentationService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPresentation(@RequestParam("file") MultipartFile file) {
        try {
            Presentation presentation = presentationService.saveFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + presentation.getFileName());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Presentation>> getAllPresentations() {
        List<Presentation> presentations = presentationService.getAllPresentations();
        return ResponseEntity.ok(presentations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePresentation(@PathVariable Long id) {
        presentationService.deletePresentation(id);
        return ResponseEntity.ok("Presentation deleted successfully");
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllPresentations() {
        presentationService.deleteAllPresentations();
        return ResponseEntity.ok("All presentations deleted successfully");
    }
}