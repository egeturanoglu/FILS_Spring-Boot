package com.fils.fils.controller;

import com.fils.fils.model.Announcement;
import com.fils.fils.repository.AnnouncementRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "http://localhost:3000")
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @PostMapping
    public ResponseEntity<String> addAnnouncement(@RequestBody Announcement announcement) {
        announcementRepository.save(announcement);
        return ResponseEntity.ok("Announcement added successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllByOrderByIdDesc();
        return ResponseEntity.ok(announcements);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id) {
        announcementRepository.deleteById(id);
        return ResponseEntity.ok("Announcement deleted successfully!");
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllAnnouncements() {
        announcementRepository.deleteAll();
        return ResponseEntity.ok("All announcements deleted successfully!");
    }
}