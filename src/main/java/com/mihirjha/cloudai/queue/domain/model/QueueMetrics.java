package com.mihirjha.cloudai.queue.domain.model;

import com.mihirjha.cloudai.queue.domain.enums.QueueStatus;
import com.mihirjha.cloudai.queue.domain.valueobject.PeopleCount;
import com.mihirjha.cloudai.queue.domain.valueobject.WaitTime;

/**
 * Represents calculated metrics for a queue.
 */
public record QueueMetrics(
        PeopleCount peopleCount,
        WaitTime estimatedWaitTimeMinutes,
        QueueStatus status
) {
}