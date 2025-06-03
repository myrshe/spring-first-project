package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByStorageFileName (String fileName);
}
