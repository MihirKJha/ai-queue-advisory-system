package com.mihirjha.cloudai.queue.api.response;

import java.time.Instant;
import java.util.UUID;

import com.mihirjha.cloudai.queue.domain.enums.QueueStatus;
import com.mihirjha.cloudai.queue.domain.enums.RecommendationType;

public record AnalyzeQueueApiResponse(
        UUID analysisId,
        Instant analyzedAt,
        String imageStorageLocation,
        int peopleCount,
        int estimatedWaitTimeMinutes,
        QueueStatus queueStatus,
        RecommendationType recommendationType,
        String recommendation,
        String reasoning) {
}