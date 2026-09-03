package com.mihirjha.cloudai.queue.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AnalyzeQueueRequest(

        @NotBlank(message = "Image file name must not be blank")
        String fileName,

        @NotBlank(message = "Image content type must not be blank")
        String contentType,

        @Positive(message = "Image size must be greater than zero")
        long sizeBytes,

        @Positive(message = "Minutes per person must be greater than zero")
        int minutesPerPerson,

        @PositiveOrZero(message = "Available time must not be negative")
        int availableTimeMinutes
) {
}