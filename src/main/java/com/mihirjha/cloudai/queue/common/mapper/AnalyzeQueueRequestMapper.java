package com.mihirjha.cloudai.queue.common.mapper;

import com.mihirjha.cloudai.queue.api.request.AnalyzeQueueRequest;
import com.mihirjha.cloudai.queue.application.service.AnalyzeQueueCommand;
import com.mihirjha.cloudai.queue.domain.enums.ImageFormat;
import org.springframework.stereotype.Component;

/**
 * Maps REST API request models to application commands.
 *
 * <p>
 * This mapper prevents transport-specific request objects from crossing
 * into the application layer.
 * </p>
 */
@Component
public class AnalyzeQueueRequestMapper {

        /**
         * Converts an API request into an application command.
         *
         * @param request   validated REST API request
         * @param imageData raw image content
         * @return application command
         */
        public AnalyzeQueueCommand toCommand(
                        AnalyzeQueueRequest request,
                        byte[] imageData) {

                return new AnalyzeQueueCommand(
                                request.fileName(),
                                request.contentType(),
                                toImageFormat(request.contentType()),
                                request.sizeBytes(),
                                imageData,
                                request.minutesPerPerson(),
                                request.availableTimeMinutes());
        }

        /**
         * Converts a supported image MIME type into the corresponding
         * domain image format.
         *
         * @param contentType image MIME type
         * @return domain image format
         */
        private ImageFormat toImageFormat(String contentType) {

                return switch (contentType.toLowerCase()) {
                        case "image/jpeg", "image/jpg" -> ImageFormat.JPEG;
                        case "image/png" -> ImageFormat.PNG;
                        default -> throw new IllegalArgumentException(
                                        "Unsupported image content type: " + contentType);
                };
        }
}