package com.mihirjha.cloudai.queue.application.service;

import com.mihirjha.cloudai.queue.domain.enums.ImageFormat;

/**
 * Application command containing the information required to analyze
 * a queue image.
 *
 * <p>
 * This command separates the application use case from the transport
 * specific REST request model.
 * </p>
 *
 * @param fileName             image file name
 * @param contentType          image MIME type
 * @param imageFormat          detected image format
 * @param sizeBytes            image size in bytes
 * @param imageData            raw image content
 * @param minutesPerPerson     estimated service duration per person
 * @param availableTimeMinutes time available to the user
 */
public record AnalyzeQueueCommand(
        String fileName,
        String contentType,
        ImageFormat imageFormat,
        long sizeBytes,
        byte[] imageData,
        int minutesPerPerson,
        int availableTimeMinutes) {
}