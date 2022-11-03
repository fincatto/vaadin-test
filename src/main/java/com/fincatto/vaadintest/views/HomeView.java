package com.fincatto.vaadintest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TimeZone;

@PageTitle("Home")
@Route(value = "")
public class HomeView extends VerticalLayout {

    private static final String BUILD_VERSION = "BUILD_VERSION";
    private static final String SESSION_COUNTER_NAME = "SESSION_COUNTER_NAME";

    public HomeView() {
        final var systemVersion = Objects.requireNonNullElse(System.getenv(BUILD_VERSION), "local");
        final var vaadinSession = VaadinSession.getCurrent();
        vaadinSession.getSession().setMaxInactiveInterval(60); //seta o tempo de sessao em segundos
        if (vaadinSession.getAttribute(SESSION_COUNTER_NAME) == null) {
            vaadinSession.setAttribute(SESSION_COUNTER_NAME, 0);
        }


        //monta info de sessoes
        this.add(new H4("Vaadin Session Info"));
        this.add(new Span("System version: " + systemVersion));
        this.add(new Span("Session ID: " + vaadinSession.getSession().getId()));
        this.add(new Span("Session creation time: " + LocalDateTime.ofInstant(Instant.ofEpochMilli(vaadinSession.getSession().getCreationTime()), TimeZone.getDefault().toZoneId())));
        this.add(new Span("Session access time: " + LocalDateTime.ofInstant(Instant.ofEpochMilli(vaadinSession.getSession().getLastAccessedTime()), TimeZone.getDefault().toZoneId())));
        this.add(new Span("Session timout seconds: " + vaadinSession.getSession().getMaxInactiveInterval()));

        //monta info de requests
        final var spanCounter = new Span(vaadinSession.getAttribute(SESSION_COUNTER_NAME).toString());
        final var button = new Button("INCREMENT");
        button.addClickListener(l -> {
            int couter = (int) vaadinSession.getAttribute(SESSION_COUNTER_NAME);
            vaadinSession.setAttribute(SESSION_COUNTER_NAME, ++couter);
            spanCounter.setText(String.valueOf(couter));
        });
        this.add(new H4("Vaadin Session Requests"));
        this.add(button);
        this.add(spanCounter);

        this.setSizeFull();
        this.setSpacing(false);
        this.setPadding(false);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}