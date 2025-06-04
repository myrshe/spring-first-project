package org.jetbrains.semwork_2sem.services;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.jetbrains.semwork_2sem.models.FileInfo;
import org.jetbrains.semwork_2sem.repository.FileInfoRepository;
import org.jetbrains.semwork_2sem.services.intefaces.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import java.io.FileInputStream;
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

    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        FileInfo fileInfo = filesInfoRepository.findByStorageFileName(fileName);
        response.setContentType(fileInfo.getType());
        try {
            IOUtils.copy(new FileInputStream(fileInfo.getUrl()), response.getOutputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}