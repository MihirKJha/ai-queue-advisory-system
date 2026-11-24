package com.mihirjha.cloudai.queue.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

/**
 * UI component for collecting queue analysis parameters.
 *
 * <p>
 * Collects service duration per person and the available time
 * for queue processing.
 * </p>
 */
public class QueueAnalysisForm extends VerticalLayout {

    private final IntegerField minutesPerPersonField;
    private final IntegerField availableTimeField;
    private final Button analyzeButton;

    /**
     * Creates and configures the queue analysis form.
     */
    public QueueAnalysisForm() {
        setPadding(false);
        setSpacing(true);
        setWidthFull();

        H3 heading = new H3("Analysis Parameters");

        minutesPerPersonField = new IntegerField("Minutes per person");
        minutesPerPersonField.setMin(1);
        minutesPerPersonField.setMax(120);
        minutesPerPersonField.setValue(5);
        minutesPerPersonField.setStepButtonsVisible(true);
        minutesPerPersonField.setWidthFull();

        availableTimeField = new IntegerField("Available time (minutes)");
        availableTimeField.setMin(1);
        availableTimeField.setMax(1440);
        availableTimeField.setValue(60);
        availableTimeField.setStepButtonsVisible(true);
        availableTimeField.setWidthFull();

        analyzeButton = new Button("Analyze Queue");
        analyzeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        analyzeButton.setWidthFull();

        add(
                heading,
                minutesPerPersonField,
                availableTimeField,
                analyzeButton);
    }

    /**
     * Returns the estimated service duration per person.
     *
     * @return minutes per person
     */
    public Integer getMinutesPerPerson() {
        return minutesPerPersonField.getValue();
    }

    /**
     * Returns the available time for queue processing.
     *
     * @return available time in minutes
     */
    public Integer getAvailableTime() {
        return availableTimeField.getValue();
    }

    /**
     * Returns the analysis button.
     *
     * @return analyze queue button
     */
    public Button getAnalyzeButton() {
        return analyzeButton;
    }

    /**
     * Checks whether the form contains valid values.
     *
     * @return true if both parameters are valid
     */
    public boolean isValidForm() {
        return minutesPerPersonField.getValue() != null
                && minutesPerPersonField.getValue() > 0
                && availableTimeField.getValue() != null
                && availableTimeField.getValue() > 0;
    }
}