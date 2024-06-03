package com.service.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    /**
     * 파일 -> url 변경
     * ex) "20240531/550e8400-e29b-41d4-a716-446655440000.jpg"
     */
    @Override
    public String toUrls(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String createdDate = now.format(dateTimeFormatter);
        String uuid = UUID.randomUUID().toString();

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return createdDate + File.separator + uuid + ext;
    }

    @Override
    public void fileUpload(MultipartFile file, String url) {
        // todo url 과 함께 s3 에 저장
    }
}
