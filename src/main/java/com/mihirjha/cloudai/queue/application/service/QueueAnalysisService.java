package com.mihirjha.cloudai.queue.application.service;

import com.mihirjha.cloudai.queue.application.workflow.QueueAnalysisWorkflow;
import com.mihirjha.cloudai.queue.domain.model.QueueAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service exposing the queue analysis use case.
 *
 * <p>
 * The service delegates workflow orchestration to
 * {@link QueueAnalysisWorkflow} while providing a stable application
 * boundary for inbound adapters.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueueAnalysisService {

        private final QueueAnalysisWorkflow queueAnalysisWorkflow;

        /**
         * Analyzes a queue image and produces an AI-powered advisory.
         *
         * @param command application command containing image and queue parameters
         * @return completed queue analysis
         */
        public QueueAnalysis analyze(AnalyzeQueueCommand command) {

                log.info("Starting queue analysis service: fileName={}", command.fileName());

                return queueAnalysisWorkflow.execute(command);
        }
}