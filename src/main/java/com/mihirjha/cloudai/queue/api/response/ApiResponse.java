package com.mihirjha.cloudai.queue.api.response;

import java.time.Instant;

public record ApiResponse<T>(
        boolean success,
        Instant timestamp,
        T data
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                Instant.now(),
                data
        );
    }
}