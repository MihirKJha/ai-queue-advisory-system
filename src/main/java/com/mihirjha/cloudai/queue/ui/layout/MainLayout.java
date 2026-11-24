package com.mihirjha.cloudai.queue.ui.layout;

import com.mihirjha.cloudai.queue.ui.view.QueueAnalysisView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

/**
 * Main application layout for the AI Queue Advisory System.
 *
 * <p>
 * Provides the shared application header, navigation drawer,
 * and content area for Vaadin views.
 * </p>
 */
public class MainLayout extends AppLayout {

        /**
         * Creates the main application layout.
         */
        public MainLayout() {
                setPrimarySection(Section.DRAWER);
                createHeader();
                createNavigationDrawer();
        }

        /**
         * Creates the application header.
         */
        private void createHeader() {
                H1 applicationTitle = new H1("AI Queue Advisory System");
                applicationTitle.getStyle()
                                .set("font-size", "var(--lumo-font-size-l)")
                                .set("margin", "0");

                Span applicationSubtitle = new Span("Intelligent Queue Analysis");
                applicationSubtitle.getStyle()
                                .set("font-size", "var(--lumo-font-size-xs)")
                                .set("color", "var(--lumo-secondary-text-color)");

                VerticalLayout titleContainer = new VerticalLayout(applicationTitle, applicationSubtitle);

                titleContainer.setPadding(false);
                titleContainer.setSpacing(false);

                addToNavbar(
                                new DrawerToggle(),
                                titleContainer);
        }

        /**
         * Creates the application navigation drawer.
         */
        private void createNavigationDrawer() {
                RouterLink queueAnalysisLink = new RouterLink(
                                "Queue Analysis",
                                QueueAnalysisView.class);

                VerticalLayout navigation = new VerticalLayout(
                                queueAnalysisLink);

                navigation.setPadding(true);
                navigation.setSpacing(true);
                navigation.setWidthFull();

                addToDrawer(navigation);
        }
}
