package com.infra.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileHelper {
    String toUrls(final MultipartFile file);

    void fileUpload(final MultipartFile file, final String url);

    void deleteFile(final String fileName);
}
