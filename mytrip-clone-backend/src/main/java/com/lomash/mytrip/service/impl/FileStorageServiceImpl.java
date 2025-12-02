package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String[] allowedExtensions = {"jpg", "jpeg", "png", "pdf"};

    @Override
    public String saveFile(MultipartFile file, String folder) {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String originalName = file.getOriginalFilename();
        String ext = getExtension(originalName);

        // validate ext
        boolean allowed = false;
        for (String e : allowedExtensions) {
            if (e.equalsIgnoreCase(ext)) {
                allowed = true;
                break;
            }
        }

        if (!allowed) {
            throw new RuntimeException("Invalid file type: " + ext);
        }

        // unique filename
        String fileName = UUID.randomUUID() + "." + ext;

        try {
            Path targetFolder = Paths.get(uploadDir, folder);

            if (!Files.exists(targetFolder)) {
                Files.createDirectories(targetFolder);
            }

            Path filePath = targetFolder.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return "/uploads/" + folder + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("File saving failed: " + e.getMessage());
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
