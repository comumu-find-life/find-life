package com.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String toUrls(MultipartFile file);

    void fileUpload(MultipartFile file, String url);
}
