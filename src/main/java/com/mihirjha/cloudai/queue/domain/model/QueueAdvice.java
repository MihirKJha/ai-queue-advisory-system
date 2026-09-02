package com.mihirjha.cloudai.queue.domain.model;

import com.mihirjha.cloudai.queue.domain.enums.RecommendationType;

/**
 * Represents the advisory generated from the queue analysis.
 */
public record QueueAdvice(
        RecommendationType recommendationType,
        String recommendation,
        String reasoning
) {
}