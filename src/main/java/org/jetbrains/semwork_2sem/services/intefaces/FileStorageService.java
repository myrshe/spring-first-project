package org.jetbrains.semwork_2sem.services.intefaces;

import org.jetbrains.semwork_2sem.models.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileInfo saveFile(MultipartFile uploadFile);
}
