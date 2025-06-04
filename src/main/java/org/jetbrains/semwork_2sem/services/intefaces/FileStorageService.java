package org.jetbrains.semwork_2sem.services.intefaces;

import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.semwork_2sem.models.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileInfo saveFile(MultipartFile uploadFile);
    void writeFileToResponse (String fileName, HttpServletResponse response);
}
