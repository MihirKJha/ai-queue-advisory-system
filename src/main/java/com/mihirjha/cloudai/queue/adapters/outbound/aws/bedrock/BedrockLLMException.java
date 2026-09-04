package com.mihirjha.cloudai.queue.adapters.outbound.aws.bedrock;

/**
 * Indicates a failure while invoking Amazon Bedrock or processing
 * its model response.
 */
public class BedrockLLMException extends RuntimeException {

        /**
         * Creates a Bedrock adapter exception.
         *
         * @param message error description
         * @param cause   underlying exception
         */
        public BedrockLLMException(
                        String message,
                        Throwable cause) {
                super(message, cause);
        }
}