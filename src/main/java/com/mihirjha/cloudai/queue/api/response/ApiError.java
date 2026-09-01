package com.mihirjha.cloudai.queue.api.response;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        boolean success,
        Instant timestamp,
        String code,
        String message,
        Map<String, String> details
) {

    public static ApiError failure(
            String code,
            String message,
            Map<String, String> details) {

        return new ApiError(
                false,
                Instant.now(),
                code,
                message,
                details
        );
    }
}