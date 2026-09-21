package com.mihirjha.cloudai.queue.common.mapper;

import com.mihirjha.cloudai.queue.api.response.AnalyzeQueueApiResponse;
import com.mihirjha.cloudai.queue.domain.model.QueueAdvice;
import com.mihirjha.cloudai.queue.domain.model.QueueAnalysis;
import com.mihirjha.cloudai.queue.domain.model.QueueMetrics;
import org.springframework.stereotype.Component;

/**
 * Maps domain queue analysis results to API response models.
 *
 * <p>
 * This mapper keeps transport-specific response structures outside
 * the application and domain layers.
 * </p>
 */
@Component
public class QueueAnalysisMapper {

        /**
         * Converts a domain queue analysis into its REST API representation.
         *
         * @param analysis completed queue analysis
         * @return API response representation
         */
        public AnalyzeQueueApiResponse toResponse(
                        QueueAnalysis analysis) {

                QueueMetrics metrics = analysis.queueMetrics();
                QueueAdvice advice = analysis.queueAdvice();

                return new AnalyzeQueueApiResponse(
                                analysis.analysisId(),
                                analysis.analyzedAt(),
                                analysis.queueImage().reference(),
                                metrics.peopleCount().value(),
                                metrics.estimatedWaitTimeMinutes().minutes(),
                                metrics.status(),
                                advice.recommendationType(),
                                advice.recommendation(),
                                advice.reasoning());
        }
}