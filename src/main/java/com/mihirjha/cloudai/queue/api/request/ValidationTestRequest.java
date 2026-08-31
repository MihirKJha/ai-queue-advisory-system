package com.mihirjha.cloudai.queue.api.request;

import jakarta.validation.constraints.NotBlank;

public record ValidationTestRequest(
        @NotBlank(message = "name must not be blank")
        String name
) {
}