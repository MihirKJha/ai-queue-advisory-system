package com.mihirjha.cloudai.queue.domain.valueobject;

public record AvailableTime(int minutes) {

    public AvailableTime {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Available time cannot be negative"
            );
        }
    }
}