package com.mihirjha.cloudai.queue.ui.view;

import com.mihirjha.cloudai.queue.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Main view for the AI Queue Advisory System.
 *
 * <p>
 * Provides the entry point for users to analyze queue images.
 * UI components will be added incrementally.
 * </p>
 */
@Route(value = "", layout = MainLayout.class)
@PageTitle("Queue Analysis | AI Queue Advisory System")
public class QueueAnalysisView extends VerticalLayout {

    /**
     * Creates the queue analysis view.
     */
    public QueueAnalysisView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H2 heading = new H2("Queue Analysis");
        Paragraph description = new Paragraph(
                "Upload a queue image to receive an AI-powered "
                        + "queue analysis and recommendation.");

        add(heading, description);
    }
}
