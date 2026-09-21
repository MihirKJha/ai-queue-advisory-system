package com.mihirjha.cloudai.queue.api.controller;

import com.mihirjha.cloudai.queue.api.request.AnalyzeQueueApiRequest;
import com.mihirjha.cloudai.queue.api.response.AnalyzeQueueApiResponse;
import com.mihirjha.cloudai.queue.api.response.ApiResponse;
import com.mihirjha.cloudai.queue.api.service.QueueAnalysisApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for queue analysis operations.
 *
 * <p>
 * The controller is responsible only for HTTP request binding,
 * validation, and response construction. Queue analysis processing
 * is delegated to the API service.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class QueueAnalysisController {

        private final QueueAnalysisApiService queueAnalysisApiService;

        /**
         * Analyzes a queue image and generates an AI-powered advisory.
         *
         * @param image   queue image uploaded by the client
         * @param request queue analysis parameters
         * @return standardized API response containing the analysis result
         * @throws IOException
         */
        @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ApiResponse<AnalyzeQueueApiResponse>> analyzeQueue(
                        @RequestPart("image") MultipartFile image,
                        @Valid @ModelAttribute AnalyzeQueueApiRequest request) {

                AnalyzeQueueApiResponse response = queueAnalysisApiService.analyze(image, request);

                return ResponseEntity.ok(ApiResponse.success(response));
        }
}