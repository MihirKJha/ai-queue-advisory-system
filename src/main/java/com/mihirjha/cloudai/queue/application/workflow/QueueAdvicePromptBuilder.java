package com.mihirjha.cloudai.queue.application.workflow;

import com.mihirjha.cloudai.queue.domain.enums.QueueStatus;
import com.mihirjha.cloudai.queue.domain.enums.RecommendationType;
import com.mihirjha.cloudai.queue.domain.valueobject.AvailableTime;
import com.mihirjha.cloudai.queue.domain.valueobject.PeopleCount;
import com.mihirjha.cloudai.queue.domain.valueobject.WaitTime;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Builds the Generative AI prompt used for queue advisory generation.
 *
 * <p>
 * The prompt template is externalized from Java source so that prompt
 * wording can evolve independently from the application workflow.
 * </p>
 */
@Component
public class QueueAdvicePromptBuilder {

        private static final String TEMPLATE_PATH = "prompts/queue-advisory.txt";

        /**
         * Builds a queue advisory prompt from the supplied queue context.
         *
         * @param peopleCount        detected number of people
         * @param waitTime           estimated waiting time
         * @param queueStatus        calculated queue status
         * @param recommendationType deterministic recommendation
         * @param availableTime      user's available time
         * @return populated Generative AI prompt
         */
        public String build(
                        PeopleCount peopleCount,
                        WaitTime waitTime,
                        QueueStatus queueStatus,
                        RecommendationType recommendationType,
                        AvailableTime availableTime) {

                String template = loadTemplate();

                return template
                                .replace("{peopleCount}",
                                                String.valueOf(peopleCount.value()))
                                .replace("{estimatedWaitTimeMinutes}",
                                                String.valueOf(waitTime.minutes()))
                                .replace("{queueStatus}",
                                                queueStatus.name())
                                .replace("{availableTimeMinutes}",
                                                String.valueOf(availableTime.minutes()))
                                .replace("{recommendationType}",
                                                recommendationType.name());
        }

        /**
         * Loads the prompt template from the application classpath.
         *
         * @return prompt template
         * @throws IllegalStateException when the template cannot be loaded
         */
        private String loadTemplate() {

                try {
                        ClassPathResource resource = new ClassPathResource(TEMPLATE_PATH);

                        return resource.getContentAsString(StandardCharsets.UTF_8);

                } catch (IOException exception) {

                        throw new IllegalStateException(
                                        "Failed to load queue advisory prompt template",
                                        exception);
                }
        }
}