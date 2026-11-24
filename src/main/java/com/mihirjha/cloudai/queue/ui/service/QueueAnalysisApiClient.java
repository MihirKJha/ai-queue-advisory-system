package com.mihirjha.cloudai.queue.ui.service;

import com.mihirjha.cloudai.queue.api.request.AnalyzeQueueMultipartRequest;
import com.mihirjha.cloudai.queue.api.response.AnalyzeQueueApiResponse;
import com.mihirjha.cloudai.queue.api.response.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

/**
 * HTTP client responsible for communicating with the Queue Analysis REST API.
 *
 * <p>
 * The Vaadin UI communicates with the backend exclusively through the
 * REST API, preserving the separation between the presentation layer
 * and the application layer.
 * </p>
 */
public class QueueAnalysisApiClient {

    private static final String ANALYZE_ENDPOINT = "/api/v1/queue/analyze";

    private final RestClient restClient;

    /**
     * Creates the API client.
     *
     * @param restClient configured Spring RestClient
     */
    public QueueAnalysisApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Submits an image and analysis parameters to the Queue Analysis API.
     *
     * @param image                uploaded image bytes
     * @param fileName             original image filename
     * @param contentType          image content type
     * @param minutesPerPerson     estimated service duration per person
     * @param availableTimeMinutes available processing time
     * @return API response containing queue analysis results
     */
    public ApiResponse<AnalyzeQueueApiResponse> analyzeQueue(
            byte[] image,
            String fileName,
            String contentType,
            int minutesPerPerson,
            int availableTimeMinutes) {

        AnalyzeQueueMultipartRequest request = new AnalyzeQueueMultipartRequest(
                minutesPerPerson,
                availableTimeMinutes);

        ByteArrayResource imageResource = new ByteArrayResource(image) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        HttpHeaders imageHeaders = new HttpHeaders();
        imageHeaders.setContentType(MediaType.parseMediaType(contentType));

        HttpEntity<ByteArrayResource> imagePart = new HttpEntity<>(imageResource, imageHeaders);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AnalyzeQueueMultipartRequest> requestPart = new HttpEntity<>(request, requestHeaders);

        LinkedMultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();

        multipartBody.add("image", imagePart);
        multipartBody.add("request", requestPart);

        return restClient.post()
                .uri(ANALYZE_ENDPOINT)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipartBody)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<AnalyzeQueueApiResponse>>() {
                });
    }
}