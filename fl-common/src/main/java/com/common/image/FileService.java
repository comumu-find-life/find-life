package com.common.image;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String toUrls(MultipartFile file);

    void fileUpload(MultipartFile file, String url);
}
