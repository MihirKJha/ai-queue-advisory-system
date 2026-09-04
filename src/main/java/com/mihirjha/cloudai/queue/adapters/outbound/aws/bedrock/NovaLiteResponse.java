package com.mihirjha.cloudai.queue.adapters.outbound.aws.bedrock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Internal response model for Amazon Nova Lite inference.
 *
 * <p>
 * The DTO mirrors only the portion of the Bedrock response required
 * by the application: generated text content.
 * </p>
 */
record NovaLiteResponse(
                Output output) {

        /**
         * Represents the model output envelope.
         *
         * @param message generated assistant message
         */
        record Output(Message message) {
        }

        /**
         * Represents the assistant response message.
         *
         * @param role    response role
         * @param content generated content blocks
         */
        record Message(
                        String role,
                        List<Content> content) {
        }

        /**
         * Represents a generated content block.
         *
         * @param text generated text, when the content block is textual
         */
        record Content(
                        @JsonProperty("text") String text) {
        }
}