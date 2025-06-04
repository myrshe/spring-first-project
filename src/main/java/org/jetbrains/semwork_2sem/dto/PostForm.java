package org.jetbrains.semwork_2sem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {

    private String text;
    private List<MultipartFile> files;
    private String tags;
}
