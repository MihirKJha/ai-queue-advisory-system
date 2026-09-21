package com.mihirjha.cloudai.queue.api.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * REST API request parameters for queue analysis.
 *
 * <p>
 * The queue image is supplied as a separate multipart file part.
 * This model contains the analysis parameters associated with that image.
 * </p>
 *
 * @param minutesPerPerson     estimated service duration per person
 * @param availableTimeMinutes time available to the user
 */
public record AnalyzeQueueApiRequest(

        @Positive(message = "Minutes per person must be greater than zero") int minutesPerPerson,

        @PositiveOrZero(message = "Available time must not be negative") int availableTimeMinutes) {
}