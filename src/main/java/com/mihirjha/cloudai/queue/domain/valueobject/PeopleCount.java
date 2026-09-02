package com.mihirjha.cloudai.queue.domain.valueobject;

public record PeopleCount(int value) {

    public PeopleCount {
        if (value < 0) {
            throw new IllegalArgumentException(
                    "People count cannot be negative"
            );
        }
    }
}