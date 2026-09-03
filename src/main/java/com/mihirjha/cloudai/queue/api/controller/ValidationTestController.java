package com.mihirjha.cloudai.queue.api.controller;

import com.mihirjha.cloudai.queue.api.request.AnalyzeQueueRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class ValidationTestController {

    @PostMapping("/validation")
    public String validate(@Valid @RequestBody AnalyzeQueueRequest request) {
        return "Validation successful";
    }
}