package com.keepu.webAPI.utils;

import org.springframework.web.multipart.MultipartFile;

public class ImageValidator {

    private static final long MAX_SIZE = 2 * 1024 * 1024; // 2MB

    public static boolean isValidImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        return (contentType.equals("image/jpeg") || contentType.equals("image/png"))
                && file.getSize() <= MAX_SIZE;
    }
}