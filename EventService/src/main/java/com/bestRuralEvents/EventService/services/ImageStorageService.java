package com.bestRuralEvents.EventService.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final Path uploadDir = Paths.get("uploads/events");

    public String saveEventImage(MultipartFile file) {
        try {
            Files.createDirectories(uploadDir);

            String originalName = file.getOriginalFilename();
            String extension = ".jpg";

            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String filename = UUID.randomUUID() + extension;
            Path targetPath = uploadDir.resolve(filename);

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/events/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Could not save image", e);
        }
    }
}