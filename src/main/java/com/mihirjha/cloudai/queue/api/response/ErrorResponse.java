package com.mihirjha.cloudai.queue.api.response;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        boolean success,
        Instant timestamp,
        String code,
        String message,
        Map<String, String> details
) {

    public static ErrorResponse failure(
            String code,
            String message,
            Map<String, String> details) {

        return new ErrorResponse(
                false,
                Instant.now(),
                code,
                message,
                details
        );
    }
}