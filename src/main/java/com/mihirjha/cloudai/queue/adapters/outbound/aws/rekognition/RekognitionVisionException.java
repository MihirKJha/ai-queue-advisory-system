package com.mihirjha.cloudai.queue.adapters.outbound.aws.rekognition;

public class RekognitionVisionException extends RuntimeException {

    public RekognitionVisionException(String message, Throwable cause) {
        super(message, cause);
    }
}