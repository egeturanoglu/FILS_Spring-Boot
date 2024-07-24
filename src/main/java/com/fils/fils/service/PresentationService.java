package com.fils.fils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fils.fils.model.Presentation;
import com.fils.fils.repository.PresentationRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PresentationService {
    private final String uploadDir = "uploads/";

    @Autowired
    private PresentationRepository presentationRepository;

    public Presentation saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Presentation presentation = new Presentation();
        presentation.setFileName(fileName);
        presentation.setFileType(file.getContentType());
        presentation.setFilePath(filePath.toString());

        return presentationRepository.save(presentation);
    }

    public List<Presentation> getAllPresentations() {
        return presentationRepository.findAll();
    }

    public void deletePresentation(Long id) {
        presentationRepository.deleteById(id);
    }

    public void deleteAllPresentations() {
        presentationRepository.deleteAll();
    }

    
}

