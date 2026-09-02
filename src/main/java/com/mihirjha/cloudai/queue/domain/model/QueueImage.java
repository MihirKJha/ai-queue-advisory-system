package com.mihirjha.cloudai.queue.domain.model;

import com.mihirjha.cloudai.queue.domain.valueobject.ImageMetadata;

/**
 * Represents metadata and a reference to the queue image
 * used for analysis.
 */
public record QueueImage(
        ImageMetadata metadata,
        String reference
) {
}