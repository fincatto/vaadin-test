package com.fincatto.vaadintest.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Homee")
@Route(value = "")
public class HomeView extends VerticalLayout {

    private static final String SESSION_COUNTER_NAME = "SESSION_COUNTER_NAME";

    public HomeView() {
        final var vaadinSession = VaadinSession.getCurrent();
        if (vaadinSession.getAttribute(SESSION_COUNTER_NAME) == null) {
            vaadinSession.setAttribute(SESSION_COUNTER_NAME, 0);
        }

        final var img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");

        final var headerTitle = new H2("Vaadin Session Test");
        final var headerSubtitle = new H4(vaadinSession.getSession().getId());
        final var spanCounter = new Span(vaadinSession.getAttribute(SESSION_COUNTER_NAME).toString());

        final var button = new Button("ADD");
        button.addClickListener(l -> {
            int couter = (int) vaadinSession.getAttribute(SESSION_COUNTER_NAME);
            vaadinSession.setAttribute(SESSION_COUNTER_NAME, ++couter);
            spanCounter.setText(String.valueOf(couter));
        });

        //monta a tela
        this.add(img);
        this.add(headerTitle);
        this.add(headerSubtitle);
        this.add(spanCounter);
        this.add(button);

        this.setSizeFull();
        this.setSpacing(false);
        this.setPadding(false);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        this.getStyle().set("text-align", "center");
    }
}