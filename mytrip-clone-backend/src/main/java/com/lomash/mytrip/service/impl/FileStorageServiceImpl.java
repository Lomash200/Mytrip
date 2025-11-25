package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String saveFile(MultipartFile file, String folder) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path folderPath = Paths.get(uploadDir + folder);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            Path filePath = folderPath.resolve(fileName);
            file.transferTo(filePath.toFile());

            return "/uploads/" + folder + "/" + fileName; // URL returned
        } catch (Exception e) {
            throw new RuntimeException("File saving failed");
        }
    }
}
