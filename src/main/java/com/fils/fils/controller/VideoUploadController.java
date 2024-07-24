package com.fils.fils.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import com.fils.fils.repository.VideoRepository; 
import com.fils.fils.model.Video; 

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "http://localhost:3000")
public class VideoUploadController {

    private static final Logger logger = Logger.getLogger(VideoUploadController.class.getName());

    @Autowired
    private VideoRepository videoRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("video") MultipartFile videoFile) {
        if (videoFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        Video video = new Video();
        try {
            logger.info("Received file: " + videoFile.getOriginalFilename());
            video.setVideoData(videoFile.getBytes());
            video.setVideoName(videoFile.getOriginalFilename());
            video.setUploadTime(LocalDateTime.now());

            videoRepository.save(video);
            logger.info("Video saved successfully");

            return ResponseEntity.ok("Video uploaded successfully");
        } catch (IOException e) {
            logger.severe("Error uploading video: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading video: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getVideoById(@PathVariable Long id) {
        Video video = videoRepository.findById(id).orElse(null);
        if (video == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(video.getVideoData());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        videoRepository.deleteById(id);
        return ResponseEntity.ok("Video deleted successfully");
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllVideos() {
        videoRepository.deleteAll();
        return ResponseEntity.ok("All videos deleted successfully");
    }

}