package com.fincatto.vaadintest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        //vaadinSession.getSession().setMaxInactiveInterval(60); //seta o tempo de sessao em segundos
        if (vaadinSession.getAttribute(SESSION_COUNTER_NAME) == null) {
            vaadinSession.setAttribute(SESSION_COUNTER_NAME, 0);
        }

        //monta info de sessoes
        this.add(new H4("Vaadin Session Info"));
        this.add(new Span("System build version: " + systemVersion));
        this.add(new Span("Session ID: " + vaadinSession.getSession().getId()));
        this.add(new Span("Session creation time: " + LocalDateTime.ofInstant(Instant.ofEpochMilli(vaadinSession.getSession().getCreationTime()), TimeZone.getDefault().toZoneId())));
        this.add(new Span("Session access time: " + LocalDateTime.ofInstant(Instant.ofEpochMilli(vaadinSession.getSession().getLastAccessedTime()), TimeZone.getDefault().toZoneId())));
        this.add(new Span("Session timout seconds: " + vaadinSession.getSession().getMaxInactiveInterval()));

        //monta info de requests
        final var spanCounter = new Span(vaadinSession.getAttribute(SESSION_COUNTER_NAME).toString());
        final var button = new Button("Increment");
        button.addClickListener(l -> {
            int couter = (int) vaadinSession.getAttribute(SESSION_COUNTER_NAME);
            vaadinSession.setAttribute(SESSION_COUNTER_NAME, ++couter);
            spanCounter.setText(String.valueOf(couter));
        });
        this.add(new H4("Vaadin Session Requests"));
        HorizontalLayout horizontalLayout = new HorizontalLayout(button, spanCounter);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        this.add(horizontalLayout);

        //monta teste de conexao
        final TextField tfUrl = new TextField("URL", "jdbc:postgresql://localhost:port/dbname");
        final TextField tfUser = new TextField("Username");
        final PasswordField pfPass = new PasswordField("Password");
        final Button btnConnect = new Button("Connect", el -> {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.err.println("Erro ao carregar driver: " + e.getLocalizedMessage());
                final Notification notification = new Notification();
                notification.setText(e.getLocalizedMessage());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.BOTTOM_START);
                notification.setDuration(10000);
                notification.open();
            }
            try (final Connection connection = DriverManager.getConnection(tfUrl.getValue(), tfUser.getValue(), pfPass.getValue())) {
                System.out.println("Sucesso: " + connection.toString());
                final Notification notification = new Notification();
                notification.setText(connection.toString());
                notification.setDuration(5000);
                notification.open();
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getLocalizedMessage());
                final Notification notification = new Notification();
                notification.setText(e.getLocalizedMessage());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.BOTTOM_START);
                notification.setDuration(10000);
                notification.open();
            }
        });
        this.add(new FormLayout(tfUrl, tfUser, pfPass, btnConnect));

        //configura pagina
        this.setSpacing(false);
    }
}