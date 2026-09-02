package com.mihirjha.cloudai.queue.domain.valueobject;

public record ServiceDuration(int minutes) {

    public ServiceDuration {
        if (minutes <= 0) {
            throw new IllegalArgumentException(
                    "Service duration must be greater than zero"
            );
        }
    }
}