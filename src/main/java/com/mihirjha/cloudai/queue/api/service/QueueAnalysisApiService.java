package com.mihirjha.cloudai.queue.api.service;

import com.mihirjha.cloudai.queue.api.request.AnalyzeQueueApiRequest;
import com.mihirjha.cloudai.queue.api.response.AnalyzeQueueApiResponse;
import com.mihirjha.cloudai.queue.application.service.AnalyzeQueueCommand;
import com.mihirjha.cloudai.queue.application.service.QueueAnalysisApplicationException;
import com.mihirjha.cloudai.queue.application.service.QueueAnalysisService;
import com.mihirjha.cloudai.queue.common.mapper.QueueAnalysisMapper;
import com.mihirjha.cloudai.queue.domain.enums.ImageFormat;
import com.mihirjha.cloudai.queue.domain.model.QueueAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * API-facing service responsible for coordinating REST input and
 * application-layer execution.
 *
 * <p>
 * This service keeps HTTP-specific concerns out of the controller
 * while delegating business execution to the application service.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueueAnalysisApiService {

        private final QueueAnalysisService queueAnalysisService;
        private final QueueAnalysisMapper queueAnalysisMapper;

        /**
         * Processes a queue analysis request received through the REST API.
         *
         * @param image   uploaded queue image
         * @param request queue analysis parameters
         * @return API response containing the completed queue analysis
         * @throws IllegalArgumentException          when the image format is
         *                                           unsupported
         * @throws QueueAnalysisApplicationException when the image content cannot be
         *                                           read
         */
        public AnalyzeQueueApiResponse analyze(
                        MultipartFile image,
                        AnalyzeQueueApiRequest request)
                        throws QueueAnalysisApplicationException {

                try {
                        AnalyzeQueueCommand command = new AnalyzeQueueCommand(
                                        image.getOriginalFilename(),
                                        image.getContentType(),
                                        determineImageFormat(image.getContentType()),
                                        image.getSize(),
                                        image.getBytes(),
                                        request.minutesPerPerson(),
                                        request.availableTimeMinutes());

                        QueueAnalysis analysis = queueAnalysisService.analyze(command);

                        return queueAnalysisMapper.toResponse(analysis);

                } catch (IOException exception) {
                        throw new QueueAnalysisApplicationException(
                                        "Unable to read uploaded queue image",
                                        exception);
                }

        }

        /**
         * Converts an uploaded image MIME type into the domain image format.
         *
         * @param contentType uploaded image content type
         * @return supported domain image format
         */
        private ImageFormat determineImageFormat(String contentType) {

                if (contentType == null) {
                        throw new IllegalArgumentException(
                                        "Image content type must not be null");
                }

                return switch (contentType.toLowerCase()) {
                        case "image/jpeg", "image/jpg" -> ImageFormat.JPEG;
                        case "image/png" -> ImageFormat.PNG;
                        default -> throw new IllegalArgumentException(
                                        "Unsupported image content type: " + contentType);
                };
        }
}