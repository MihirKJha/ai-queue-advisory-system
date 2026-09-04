package com.mihirjha.cloudai.queue.adapters.outbound.aws.s3;

import com.mihirjha.cloudai.queue.configuration.AwsProperties;
import com.mihirjha.cloudai.queue.ports.outbound.StorageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3StorageAdapter implements StorageProvider {

        private final S3Client s3Client;
        private final AwsProperties awsProperties;

        @Override
        public String store(
                        String fileName,
                        String contentType,
                        byte[] content) {

                String bucketName = awsProperties.s3().bucketName();
                String objectKey = generateObjectKey(fileName);

                PutObjectRequest request = PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(objectKey)
                                .contentType(contentType)
                                .contentLength((long) content.length)
                                .build();

                try {
                        log.info(
                                        "Uploading queue image to S3: bucket={}, key={}",
                                        bucketName,
                                        objectKey);

                        s3Client.putObject(
                                        request,
                                        RequestBody.fromBytes(content));

                        String location = "s3://" + bucketName + "/" + objectKey;

                        log.info(
                                        "Queue image uploaded successfully: {}",
                                        location);

                        return location;

                } catch (RuntimeException exception) {

                        log.error("Failed to upload queue image to S3: bucket={}, key={}",
                                        bucketName,
                                        objectKey,
                                        exception);

                        throw new S3StorageException(
                                        "Failed to store queue image",
                                        exception);
                }
        }

        private String generateObjectKey(String fileName) {
                return "queue-images/"
                                + UUID.randomUUID()
                                + "/"
                                + fileName;
        }
}