package com.mihirjha.cloudai.queue.api.exception;

import com.mihirjha.cloudai.queue.api.response.ErrorResponse;
import com.mihirjha.cloudai.queue.application.service.QueueAnalysisApplicationException;
import com.mihirjha.cloudai.queue.domain.exception.InvalidQueueImageException;
import com.mihirjha.cloudai.queue.domain.exception.InvalidWaitingTimeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Centralized exception handler for the REST API.
 *
 * <p>
 * This component translates application, domain, validation, and
 * infrastructure-related exceptions into a consistent API error response.
 * Controllers therefore remain focused only on HTTP request binding and
 * delegation.
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Handles bean validation failures on API request models.
         *
         * @param exception validation exception
         * @return standardized validation error response
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleValidationException(
                        MethodArgumentNotValidException exception) {

                Map<String, String> errors = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .collect(Collectors.toMap(
                                                error -> error.getField(),
                                                error -> error.getDefaultMessage(),
                                                (first, second) -> first));

                return ErrorResponse.failure(
                                "VALIDATION_ERROR",
                                "Request validation failed",
                                errors);
        }

        /**
         * Handles missing multipart request parts.
         *
         * @param exception missing request part exception
         * @return standardized client error response
         */
        @ExceptionHandler(MissingServletRequestPartException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleMissingRequestPart(
                        MissingServletRequestPartException exception) {

                return ErrorResponse.failure(
                                "MISSING_REQUEST_PART",
                                "Required request part is missing",
                                Map.of("part", exception.getRequestPartName()));
        }

        /**
         * Handles missing request parameters.
         *
         * @param exception missing parameter exception
         * @return standardized client error response
         */
        @ExceptionHandler(MissingServletRequestParameterException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleMissingRequestParameter(
                        MissingServletRequestParameterException exception) {

                return ErrorResponse.failure(
                                "MISSING_REQUEST_PARAMETER",
                                "Required request parameter is missing",
                                Map.of("parameter", exception.getParameterName()));
        }

        /**
         * Handles malformed or unreadable HTTP request bodies.
         *
         * @param exception unreadable request exception
         * @return standardized client error response
         */
        @ExceptionHandler(HttpMessageNotReadableException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleMessageNotReadable(
                        HttpMessageNotReadableException exception) {

                return ErrorResponse.failure(
                                "INVALID_REQUEST",
                                "Request body could not be read",
                                Map.of());
        }

        /**
         * Handles invalid arguments originating from API boundary validation.
         *
         * @param exception illegal argument exception
         * @return standardized client error response
         */
        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleIllegalArgument(
                        IllegalArgumentException exception) {

                return ErrorResponse.failure(
                                "INVALID_ARGUMENT",
                                exception.getMessage(),
                                Map.of());
        }

        /**
         * Handles invalid queue image requests.
         *
         * @param exception domain validation exception
         * @return standardized client error response
         */
        @ExceptionHandler(InvalidQueueImageException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleInvalidQueueImage(
                        InvalidQueueImageException exception) {

                return ErrorResponse.failure(
                                "INVALID_QUEUE_IMAGE",
                                exception.getMessage(),
                                Map.of());
        }

        /**
         * Handles invalid service duration or available-time values.
         *
         * @param exception domain validation exception
         * @return standardized client error response
         */
        @ExceptionHandler(InvalidWaitingTimeException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleInvalidWaitingTime(
                        InvalidWaitingTimeException exception) {

                return ErrorResponse.failure(
                                "INVALID_WAITING_TIME",
                                exception.getMessage(),
                                Map.of());
        }

        /**
         * Handles application-level failures.
         *
         * <p>
         * The underlying exception is logged for diagnostics while the
         * client receives a stable application-level error message.
         * </p>
         *
         * @param exception application exception
         * @return standardized application error response
         */
        @ExceptionHandler(QueueAnalysisApplicationException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleApplicationException(
                        QueueAnalysisApplicationException exception) {

                log.error(
                                "Queue analysis application failure",
                                exception);

                return ErrorResponse.failure(
                                "APPLICATION_ERROR",
                                "Queue analysis could not be completed",
                                Map.of());
        }

        /**
         * Handles failures while reading uploaded image content.
         *
         * <p>
         * This is primarily a defensive boundary handler. The API service
         * should normally translate this checked exception before it reaches
         * the controller boundary.
         * </p>
         *
         * @param exception I/O exception
         * @return standardized server error response
         */
        @ExceptionHandler(IOException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleIOException(IOException exception) {

                log.error(
                                "I/O failure while processing queue analysis request",
                                exception);

                return ErrorResponse.failure(
                                "IMAGE_READ_ERROR",
                                "Unable to process the uploaded image",
                                Map.of());
        }

        /**
         * Handles uploaded files exceeding the configured request size.
         *
         * @param exception upload size exception
         * @return standardized payload-size error response
         */
        @ExceptionHandler(MaxUploadSizeExceededException.class)
        @ResponseStatus(HttpStatus.CONTENT_TOO_LARGE)
        public ErrorResponse handleMaxUploadSizeExceeded(
                        MaxUploadSizeExceededException exception) {

                return ErrorResponse.failure(
                                "FILE_TOO_LARGE",
                                "Uploaded file exceeds the maximum allowed size",
                                Map.of());
        }

        /**
         * Handles unexpected exceptions that are not explicitly classified.
         *
         * <p>
         * The full exception is logged for server-side diagnostics, while
         * the response deliberately avoids exposing internal implementation
         * details to the API client.
         * </p>
         *
         * @param exception unexpected exception
         * @param request   current HTTP request
         * @return standardized internal server error response
         */
        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleUnexpectedException(
                        Exception exception,
                        HttpServletRequest request) {

                log.error(
                                "Unexpected API error: method={}, uri={}",
                                request.getMethod(),
                                request.getRequestURI(),
                                exception);

                return ErrorResponse.failure(
                                "INTERNAL_SERVER_ERROR",
                                "An unexpected error occurred",
                                Map.of());
        }
}