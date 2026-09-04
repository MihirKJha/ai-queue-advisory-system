package com.mihirjha.cloudai.queue.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsClientConfig {

    @Bean
    public S3Client s3Client(AwsProperties awsProperties) {
        return S3Client.builder()
                .region(Region.of(awsProperties.region()))
                .build();
    }

    @Bean
    public RekognitionClient rekognitionClient(
            AwsProperties awsProperties) {

        return RekognitionClient.builder()
                .region(Region.of(awsProperties.region()))
                .build();
    }

    /**
     * Creates the Amazon Bedrock Runtime client used by the
     * Generative AI adapter.
     *
     * @param awsProperties application AWS configuration
     * @return configured Bedrock Runtime client
     */
    @Bean
    public BedrockRuntimeClient bedrockRuntimeClient(
            AwsProperties awsProperties) {

        return BedrockRuntimeClient.builder()
                .region(Region.of(awsProperties.region()))
                .build();
    }
}