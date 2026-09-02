package com.mihirjha.cloudai.queue.domain.exception;

public class QueueAnalysisException extends RuntimeException {

    public QueueAnalysisException(String message) {
        super(message);
    }

    public QueueAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}