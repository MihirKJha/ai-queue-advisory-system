package com.mihirjha.cloudai.queue.domain.exception;

public class InvalidWaitingTimeException extends QueueAnalysisException {

    public InvalidWaitingTimeException(String message) {
        super(message);
    }
}