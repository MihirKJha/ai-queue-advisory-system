package com.mihirjha.cloudai.queue.adapters.outbound.aws.bedrock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Internal request model for Amazon Nova Lite inference.
 *
 * <p>
 * This DTO is intentionally scoped to the Bedrock adapter because the
 * structure represents an AWS model-specific request contract.
 * </p>
 *
 * @param schemaVersion   Nova messages schema version
 * @param messages        conversation messages sent to the model
 * @param inferenceConfig model inference configuration
 */
record NovaLiteRequest(
                @JsonProperty("schemaVersion") String schemaVersion,

                @JsonProperty("messages") List<Message> messages,

                @JsonProperty("inferenceConfig") InferenceConfig inferenceConfig) {

        /**
         * Represents a message submitted to the Nova model.
         *
         * @param role    message role
         * @param content message content blocks
         */
        record Message(
                        String role,
                        List<Content> content) {
        }

        /**
         * Represents a text content block.
         *
         * @param text text supplied to the model
         */
        record Content(String text) {
        }

        /**
         * Controls model inference behavior.
         *
         * @param maxTokens   maximum number of generated tokens
         * @param temperature controls response randomness
         */
        record InferenceConfig(
                        int maxTokens,
                        double temperature) {
        }
}