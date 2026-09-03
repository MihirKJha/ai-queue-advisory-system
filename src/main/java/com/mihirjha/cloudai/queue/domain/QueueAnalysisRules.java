package com.mihirjha.cloudai.queue.domain;

import com.mihirjha.cloudai.queue.domain.enums.ImageFormat;
import com.mihirjha.cloudai.queue.domain.enums.QueueStatus;
import com.mihirjha.cloudai.queue.domain.enums.RecommendationType;
import com.mihirjha.cloudai.queue.domain.exception.InvalidQueueImageException;
import com.mihirjha.cloudai.queue.domain.exception.InvalidWaitingTimeException;
import com.mihirjha.cloudai.queue.domain.exception.QueueAnalysisException;
import com.mihirjha.cloudai.queue.domain.valueobject.AvailableTime;
import com.mihirjha.cloudai.queue.domain.valueobject.ServiceDuration;
import com.mihirjha.cloudai.queue.domain.valueobject.WaitTime;

public final class QueueAnalysisRules {

    private static final long MAX_IMAGE_SIZE_BYTES = 10 * 1024 * 1024;

    private QueueAnalysisRules() {
    }

    public static void validateImage(
            ImageFormat format,
            long sizeBytes) {

        if (format == null) {
            throw new InvalidQueueImageException(
                    "Image format must not be null");
        }

        if (format != ImageFormat.JPEG
                && format != ImageFormat.PNG) {
            throw new InvalidQueueImageException(
                    "Unsupported image format: " + format);
        }

        if (sizeBytes <= 0) {
            throw new InvalidQueueImageException(
                    "Image size must be greater than zero");
        }

        if (sizeBytes > MAX_IMAGE_SIZE_BYTES) {
            throw new InvalidQueueImageException(
                    "Image size must not exceed 10 MB");
        }
    }

    public static void validateServiceDuration(
            ServiceDuration serviceDuration) {

        if (serviceDuration == null) {
            throw new InvalidWaitingTimeException(
                    "Service duration must not be null");
        }

        if (serviceDuration.minutes() <= 0) {
            throw new InvalidWaitingTimeException(
                    "Service duration must be greater than zero");
        }
    }

    public static void validateAvailableTime(
            AvailableTime availableTime) {

        if (availableTime == null) {
            throw new InvalidWaitingTimeException(
                    "Available time must not be null");
        }

        if (availableTime.minutes() < 0) {
            throw new InvalidWaitingTimeException(
                    "Available time cannot be negative");
        }
    }

    public static int calculateEstimatedWaitTime(
            int peopleCount,
            ServiceDuration serviceDuration) {

        if (peopleCount < 0) {
            throw new QueueAnalysisException(
                    "People count cannot be negative");
        }

        validateServiceDuration(serviceDuration);

        return peopleCount * serviceDuration.minutes();
    }

    public static QueueStatus determineQueueStatus(int peopleCount) {

        if (peopleCount < 0) {
            throw new QueueAnalysisException(
                    "People count cannot be negative");
        }

        if (peopleCount <= 5) {
            return QueueStatus.LOW;
        }

        if (peopleCount <= 10) {
            return QueueStatus.MODERATE;
        }

        return QueueStatus.HIGH;
    }

    public static RecommendationType determineRecommendation(
            QueueStatus queueStatus,
            WaitTime estimatedWaitTime,
            AvailableTime availableTime) {

        if (queueStatus == null) {
            throw new QueueAnalysisException(
                    "Queue status must not be null");
        }

        if (estimatedWaitTime == null) {
            throw new QueueAnalysisException(
                    "Estimated wait time must not be null");
        }

        validateAvailableTime(availableTime);

        if (queueStatus == QueueStatus.HIGH) {
            return RecommendationType.AVOID;
        }

        if (estimatedWaitTime.minutes() <= availableTime.minutes()) {
            return RecommendationType.PROCEED;
        }

        return RecommendationType.WAIT;
    }

}