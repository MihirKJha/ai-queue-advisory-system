package com.mihirjha.cloudai.queue.adapters.outbound.aws.rekognition;

import com.mihirjha.cloudai.queue.domain.valueobject.PeopleCount;
import com.mihirjha.cloudai.queue.ports.outbound.VisionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

@Slf4j
@Component
@RequiredArgsConstructor
public class RekognitionVisionAdapter implements VisionProvider {

        private static final String PERSON_LABEL = "Person";

        private final RekognitionClient rekognitionClient;

        @Override
        public PeopleCount detectPeople(byte[] imageData) {

                if (imageData == null || imageData.length == 0) {
                        throw new IllegalArgumentException(
                                        "Image data must not be null or empty");
                }

                try {
                        Image image = Image.builder()
                                        .bytes(SdkBytes.fromByteArray(imageData))
                                        .build();

                        DetectLabelsRequest request = DetectLabelsRequest.builder()
                                        .image(image)
                                        .maxLabels(100)
                                        .build();

                        log.info("Analyzing queue image with Amazon Rekognition");

                        DetectLabelsResponse response = rekognitionClient.detectLabels(request);

                        int peopleCount = response.labels()
                                        .stream()
                                        .filter(label -> PERSON_LABEL.equals(label.name()))
                                        .flatMap(label -> label.instances().stream())
                                        .mapToInt(instance -> 1)
                                        .sum();

                        log.info(
                                        "Amazon Rekognition detected {} people",
                                        peopleCount);

                        return new PeopleCount(peopleCount);

                } catch (RuntimeException exception) {

                        log.error(
                                        "Failed to analyze queue image with Amazon Rekognition",
                                        exception);

                        throw new RekognitionVisionException(
                                        "Failed to analyze queue image",
                                        exception);
                }
        }
}