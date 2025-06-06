package org.jetbrains.semwork_2sem.services;

import org.jetbrains.semwork_2sem.models.FileInfo;
import org.jetbrains.semwork_2sem.repository.FileInfoRepository;
import org.jetbrains.semwork_2sem.services.intefaces.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileInfoRepository filesInfoRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Override
    public FileInfo saveFile(MultipartFile uploadFile) {
        if (uploadFile.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String storageName = UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();
        // + "." + FilenameUtils.getExtention(uploadFile.getOriginalFilename());

        FileInfo file = FileInfo.builder()
                .type(uploadFile.getContentType())
                .originalFileName(uploadFile.getOriginalFilename())
                .size(uploadFile.getSize())
                .storageFileName(storageName)
                .posts(new ArrayList<>())
                .url(storagePath + "\\" + storageName)
                .build();


        try {
            Files.copy(uploadFile.getInputStream(), Paths.get(storagePath, storageName));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
//        filesInfoRepository.save(file);
        return file;
    }
}