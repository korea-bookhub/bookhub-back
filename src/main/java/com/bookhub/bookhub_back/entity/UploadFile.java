package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.FileTargetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "upload_Files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_name", nullable = false)
    private String originalName;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_path", nullable = false)
    private String filePath;
    @Column(name = "file_type", nullable = false)
    private String fileType;
    @Column(name = "file_size", nullable = false)
    private Long fileSize;
    @Column(name = "target_id", nullable = false)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private FileTargetType targetType;

    @Builder
    public UploadFile(String originalName, String fileName, String filePath,
                      String fileType, long fileSize, String targetId,
                      FileTargetType targetType
                      ) {
        this.originalName = originalName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.targetId = targetId;
        this.targetType = targetType;
    }

}
