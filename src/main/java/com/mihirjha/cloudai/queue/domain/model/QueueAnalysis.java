package com.mihirjha.cloudai.queue.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents the completed analysis of a queue.
 */
public record QueueAnalysis(
        UUID analysisId,
        Instant analyzedAt,
        QueueImage queueImage,
        QueueMetrics queueMetrics,
        QueueAdvice queueAdvice
) {
}