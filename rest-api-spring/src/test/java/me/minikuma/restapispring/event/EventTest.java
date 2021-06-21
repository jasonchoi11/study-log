package me.minikuma.restapispring.event;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("REST API")
                .description("REST API With Sprig Boot!")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        Event event = new Event();

        String name = "Event";
        String description = "Event Description";

        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }
}