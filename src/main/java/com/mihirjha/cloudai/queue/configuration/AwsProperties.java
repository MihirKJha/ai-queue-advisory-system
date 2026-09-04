package com.mihirjha.cloudai.queue.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsProperties(
        String region,
        S3Properties s3
) {

    public record S3Properties(
            String bucketName
    ) {}
}