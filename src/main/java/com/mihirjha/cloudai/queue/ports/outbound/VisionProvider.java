package com.mihirjha.cloudai.queue.ports.outbound;

import com.mihirjha.cloudai.queue.domain.valueobject.PeopleCount;

public interface VisionProvider {

    PeopleCount detectPeople(byte[] imageData);
}