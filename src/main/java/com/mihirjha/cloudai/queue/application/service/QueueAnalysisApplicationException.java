package com.mihirjha.cloudai.queue.application.service;

/**
 * Represents an application-level failure while executing the
 * queue analysis use case.
 *
 * <p>
 * Infrastructure-specific exceptions are translated into this
 * exception before crossing the application boundary.
 * </p>
 */
public class QueueAnalysisApplicationException extends RuntimeException {

        /**
         * Creates an application exception.
         *
         * @param message descriptive error message
         * @param cause   underlying failure
         */
        public QueueAnalysisApplicationException(
                        String message,
                        Throwable cause) {
                super(message, cause);
        }
}