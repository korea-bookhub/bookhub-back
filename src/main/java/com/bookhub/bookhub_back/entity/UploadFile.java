package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.FileTargetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "uploadFiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;

    private Long targetId;
    private FileTargetType fileTargetType;

}
