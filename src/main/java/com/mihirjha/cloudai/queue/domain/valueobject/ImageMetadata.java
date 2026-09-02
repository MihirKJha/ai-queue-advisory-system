package com.mihirjha.cloudai.queue.domain.valueobject;

import com.mihirjha.cloudai.queue.domain.enums.ImageFormat;

public record ImageMetadata(
        String fileName,
        ImageFormat format,
        long sizeBytes
) {

    public ImageMetadata {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException(
                    "Image file name must not be blank"
            );
        }

        if (format == null) {
            throw new IllegalArgumentException(
                    "Image format must not be null"
            );
        }

        if (sizeBytes <= 0) {
            throw new IllegalArgumentException(
                    "Image size must be greater than zero"
            );
        }
    }
}