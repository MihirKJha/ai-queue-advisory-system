package com.mihirjha.cloudai.queue.domain.valueobject;

public record WaitTime(int minutes) {

    public WaitTime {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Wait time cannot be negative"
            );
        }
    }
}