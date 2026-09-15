package com.mihirjha.cloudai.queue.application.workflow;

import com.mihirjha.cloudai.queue.application.service.AnalyzeQueueCommand;
import com.mihirjha.cloudai.queue.domain.QueueAnalysisRules;
import com.mihirjha.cloudai.queue.domain.enums.QueueStatus;
import com.mihirjha.cloudai.queue.domain.enums.RecommendationType;
import com.mihirjha.cloudai.queue.domain.model.QueueAdvice;
import com.mihirjha.cloudai.queue.domain.model.QueueAnalysis;
import com.mihirjha.cloudai.queue.domain.model.QueueImage;
import com.mihirjha.cloudai.queue.domain.model.QueueMetrics;
import com.mihirjha.cloudai.queue.domain.valueobject.AvailableTime;
import com.mihirjha.cloudai.queue.domain.valueobject.ImageMetadata;
import com.mihirjha.cloudai.queue.domain.valueobject.PeopleCount;
import com.mihirjha.cloudai.queue.domain.valueobject.ServiceDuration;
import com.mihirjha.cloudai.queue.domain.valueobject.WaitTime;
import com.mihirjha.cloudai.queue.ports.outbound.LLMProvider;
import com.mihirjha.cloudai.queue.ports.outbound.StorageProvider;
import com.mihirjha.cloudai.queue.ports.outbound.VisionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/**
 * Orchestrates the queue analysis business workflow.
 *
 * <p>
 * The workflow coordinates domain rules and cloud-independent capability
 * ports to transform an analysis command into a {@link QueueAnalysis}.
 * </p>
 *
 * <p>
 * No Cloud SDK types are used here. Cloud-specific implementations are
 * supplied through {@link StorageProvider}, {@link VisionProvider}, and
 * {@link LLMProvider}.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QueueAnalysisWorkflow {

        private final StorageProvider storageProvider;
        private final VisionProvider visionProvider;
        private final LLMProvider llmProvider;
        private final QueueAdvicePromptBuilder queueAdvicePromptBuilder;

        /**
         * Executes the queue analysis workflow.
         *
         * @param command application command containing image and queue parameters
         * @return completed queue analysis
         */
        public QueueAnalysis execute(AnalyzeQueueCommand command) {

                log.info("Executing queue analysis workflow: fileName={}",
                                command.fileName());

                // 1. Validate image metadata and size.
                QueueAnalysisRules.validateImage(
                                command.imageFormat(),
                                command.sizeBytes());

                // 2. Build domain image metadata.
                ImageMetadata imageMetadata = new ImageMetadata(
                                command.fileName(),
                                command.imageFormat(),
                                command.sizeBytes());

                // 3. Store the image.
                String storageReference = storageProvider.store(
                                command.fileName(),
                                command.contentType(),
                                command.imageData());

                QueueImage queueImage = new QueueImage(
                                imageMetadata,
                                storageReference);

                // 4. Detect people using the configured vision provider.
                PeopleCount peopleCount = visionProvider.detectPeople(command.imageData());

                // 5. Calculate estimated waiting time.
                ServiceDuration serviceDuration = new ServiceDuration(command.minutesPerPerson());

                AvailableTime availableTime = new AvailableTime(command.availableTimeMinutes());

                int estimatedWaitMinutes = QueueAnalysisRules.calculateEstimatedWaitTime(
                                peopleCount.value(),
                                serviceDuration);

                WaitTime waitTime = new WaitTime(estimatedWaitMinutes);

                // 6. Determine queue status.
                QueueStatus queueStatus = QueueAnalysisRules.determineQueueStatus(
                                peopleCount.value());

                // 7. Determine recommendation.
                RecommendationType recommendationType = QueueAnalysisRules.determineRecommendation(
                                queueStatus,
                                waitTime,
                                availableTime);

                // 8. Generate AI advisory.
                String prompt = queueAdvicePromptBuilder.build(
                                peopleCount,
                                waitTime,
                                queueStatus,
                                recommendationType,
                                availableTime);

                String recommendation = llmProvider.generateAdvice(prompt);

                QueueAdvice queueAdvice = new QueueAdvice(
                                recommendationType,
                                recommendation,
                                "Recommendation generated using queue metrics and available time.");

                QueueMetrics queueMetrics = new QueueMetrics(
                                peopleCount,
                                waitTime,
                                queueStatus);

                QueueAnalysis analysis = new QueueAnalysis(
                                UUID.randomUUID(),
                                Instant.now(),
                                queueImage,
                                queueMetrics,
                                queueAdvice);

                log.info("Queue analysis workflow completed: analysisId={}, peopleCount={}, status={}",
                                analysis.analysisId(),
                                peopleCount.value(),
                                queueStatus);

                return analysis;
        }

}