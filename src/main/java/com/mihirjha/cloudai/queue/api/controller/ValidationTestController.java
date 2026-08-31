package com.mihirjha.cloudai.queue.api.controller;

import com.mihirjha.cloudai.queue.api.request.ValidationTestRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class ValidationTestController {

    @PostMapping("/validation")
    public String validate(@Valid @RequestBody ValidationTestRequest request) {
        return "Validation successful";
    }
}