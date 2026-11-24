package com.mihirjha.cloudai.queue.ui.component;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * UI component responsible for displaying queue analysis results.
 *
 * <p>
 * Displays queue metrics, status, AI recommendations,
 * analysis reasoning, and image storage information.
 * </p>
 *
 * <p>
 * This component is presentation-only and does not contain
 * business logic or communicate with the backend.
 * </p>
 */
public class QueueResultComponent extends VerticalLayout {

    private final Span peopleCountValue;
    private final Span estimatedWaitValue;
    private final Span queueStatusValue;

    private final Paragraph recommendationValue;
    private final Paragraph reasoningValue;
    private final Paragraph storageLocationValue;

    /**
     * Creates and configures the queue result component.
     */
    public QueueResultComponent() {
        setPadding(true);
        setSpacing(true);
        setWidthFull();

        getStyle()
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("border-radius", "var(--lumo-border-radius-l)");

        H3 heading = new H3("Queue Analysis Result");

        peopleCountValue = createMetricValue("Not available");
        estimatedWaitValue = createMetricValue("Not available");
        queueStatusValue = createMetricValue("Not available");

        recommendationValue = new Paragraph("Not available");
        reasoningValue = new Paragraph("Not available");
        storageLocationValue = new Paragraph("Not available");

        add(
                heading,
                createMetricSection("People Detected", peopleCountValue),
                createMetricSection("Estimated Waiting Time", estimatedWaitValue),
                createMetricSection("Queue Status", queueStatusValue),
                createTextSection("AI Recommendation", recommendationValue),
                createTextSection("Analysis Reasoning", reasoningValue),
                createTextSection("Image Storage Location", storageLocationValue));

        setVisible(false);
    }

    /**
     * Creates a metric value component.
     *
     * @param initialValue initial displayed value
     * @return metric value component
     */
    private Span createMetricValue(String initialValue) {
        Span value = new Span(initialValue);

        value.getStyle()
                .set("font-size", "var(--lumo-font-size-xl)")
                .set("font-weight", "bold");

        return value;
    }

    /**
     * Creates a section for displaying a metric.
     *
     * @param title section title
     * @param value metric value component
     * @return metric section layout
     */
    private VerticalLayout createMetricSection(
            String title,
            Span value) {

        H4 sectionTitle = new H4(title);

        VerticalLayout section = new VerticalLayout(
                sectionTitle,
                value);

        section.setPadding(false);
        section.setSpacing(false);
        section.setWidthFull();

        return section;
    }

    /**
     * Creates a section for displaying text content.
     *
     * @param title   section title
     * @param content text content component
     * @return text section layout
     */
    private VerticalLayout createTextSection(
            String title,
            Paragraph content) {

        H4 sectionTitle = new H4(title);

        VerticalLayout section = new VerticalLayout(
                sectionTitle,
                content);

        section.setPadding(false);
        section.setSpacing(false);
        section.setWidthFull();

        return section;
    }

    /**
     * Updates the displayed people count.
     *
     * @param peopleCount number of people detected
     */
    public void setPeopleCount(int peopleCount) {
        peopleCountValue.setText(String.valueOf(peopleCount));
    }

    /**
     * Updates the estimated waiting time.
     *
     * @param estimatedWaitMinutes estimated wait in minutes
     */
    public void setEstimatedWait(int estimatedWaitMinutes) {
        estimatedWaitValue.setText(
                estimatedWaitMinutes + " minutes");
    }

    /**
     * Updates the queue status.
     *
     * @param queueStatus queue status description
     */
    public void setQueueStatus(String queueStatus) {
        queueStatusValue.setText(queueStatus);
    }

    /**
     * Updates the AI recommendation.
     *
     * @param recommendation recommendation text
     */
    public void setRecommendation(String recommendation) {
        recommendationValue.setText(recommendation);
    }

    /**
     * Updates the analysis reasoning.
     *
     * @param reasoning analysis explanation
     */
    public void setReasoning(String reasoning) {
        reasoningValue.setText(reasoning);
    }

    /**
     * Updates the image storage location.
     *
     * @param storageLocation image storage reference or location
     */
    public void setStorageLocation(String storageLocation) {
        storageLocationValue.setText(storageLocation);
    }

    /**
     * Clears the displayed result values.
     */
    public void clearResults() {
        peopleCountValue.setText("Not available");
        estimatedWaitValue.setText("Not available");
        queueStatusValue.setText("Not available");

        recommendationValue.setText("Not available");
        reasoningValue.setText("Not available");
        storageLocationValue.setText("Not available");
    }
}