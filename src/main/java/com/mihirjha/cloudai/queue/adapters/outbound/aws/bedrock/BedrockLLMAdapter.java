package com.mihirjha.cloudai.queue.adapters.outbound.aws.bedrock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihirjha.cloudai.queue.ports.outbound.LLMProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * AWS Bedrock implementation of the {@link LLMProvider} capability.
 *
 * <p>
 * This adapter isolates Amazon Bedrock Runtime SDK types and
 * Nova-specific request/response structures from the application
 * and domain layers.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BedrockLLMAdapter implements LLMProvider {

        private static final String MODEL_ID = "amazon.nova-lite-v1:0";
        private static final String SCHEMA_VERSION = "messages-v1";
        private static final String USER_ROLE = "user";

        private static final int MAX_TOKENS = 300;
        private static final double TEMPERATURE = 0.3;

        private final BedrockRuntimeClient bedrockRuntimeClient;
        private final ObjectMapper objectMapper;

        /**
         * Generates queue advisory text using Amazon Bedrock.
         *
         * @param prompt prompt containing the queue analysis context
         * @return generated advisory text
         * @throws BedrockLLMException when inference or response processing fails
         */
        @Override
        public String generateAdvice(String prompt) {

                if (prompt == null || prompt.isBlank()) {
                        throw new IllegalArgumentException(
                                        "Prompt must not be null or blank");
                }

                try {
                        String requestBody = buildRequestBody(prompt);

                        InvokeModelRequest request = InvokeModelRequest.builder()
                                        .modelId(MODEL_ID)
                                        .contentType("application/json")
                                        .accept("application/json")
                                        .body(SdkBytes.fromString(
                                                        requestBody,
                                                        StandardCharsets.UTF_8))
                                        .build();

                        log.info(
                                        "Invoking Amazon Bedrock model: modelId={}",
                                        MODEL_ID);

                        InvokeModelResponse response = bedrockRuntimeClient.invokeModel(request);

                        return extractGeneratedText(response);

                } catch (BedrockLLMException exception) {
                        throw exception;

                } catch (RuntimeException exception) {

                        log.error(
                                        "Failed to generate queue advisory using Amazon Bedrock",
                                        exception);

                        throw new BedrockLLMException(
                                        "Failed to generate queue advisory",
                                        exception);
                }
        }

        /**
         * Builds the Nova Lite model-specific request body.
         *
         * @param prompt user prompt
         * @return serialized JSON request
         */
        private String buildRequestBody(String prompt) {

                NovaLiteRequest request = new NovaLiteRequest(
                                SCHEMA_VERSION,
                                List.of(
                                                new NovaLiteRequest.Message(
                                                                USER_ROLE,
                                                                List.of(
                                                                                new NovaLiteRequest.Content(prompt)))),
                                new NovaLiteRequest.InferenceConfig(
                                                MAX_TOKENS,
                                                TEMPERATURE));

                try {
                        return objectMapper.writeValueAsString(request);

                } catch (JsonProcessingException exception) {

                        throw new BedrockLLMException(
                                        "Failed to serialize Bedrock request",
                                        exception);
                }
        }

        /**
         * Extracts the generated textual response from the Bedrock response.
         *
         * @param response raw Bedrock Runtime response
         * @return generated model text
         */
        private String extractGeneratedText(
                        InvokeModelResponse response) {

                if (response == null || response.body() == null) {
                        throw new BedrockLLMException(
                                        "Amazon Bedrock returned an empty response",
                                        null);
                }

                try {
                        NovaLiteResponse novaResponse = objectMapper.readValue(
                                        response.body().asUtf8String(),
                                        NovaLiteResponse.class);

                        if (novaResponse.output() == null
                                        || novaResponse.output().message() == null
                                        || novaResponse.output().message().content() == null) {

                                throw new BedrockLLMException(
                                                "Amazon Bedrock response did not contain output content",
                                                null);
                        }

                        return novaResponse.output()
                                        .message()
                                        .content()
                                        .stream()
                                        .map(NovaLiteResponse.Content::text)
                                        .filter(text -> text != null && !text.isBlank())
                                        .findFirst()
                                        .orElseThrow(() -> new BedrockLLMException(
                                                        "Amazon Bedrock response did not contain generated text",
                                                        null));

                } catch (JsonProcessingException exception) {

                        throw new BedrockLLMException(
                                        "Failed to parse Amazon Bedrock response",
                                        exception);
                }
        }
}