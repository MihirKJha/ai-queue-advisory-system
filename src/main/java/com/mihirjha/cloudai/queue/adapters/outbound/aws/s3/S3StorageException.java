package com.mihirjha.cloudai.queue.adapters.outbound.aws.s3;

public class S3StorageException extends RuntimeException {

        public S3StorageException(String message, Throwable cause) {
                super(message, cause);
        }
}