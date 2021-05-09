package me.minikuma.restapispring.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {
    @Test
    @DisplayName("Event 클래스에서 Builder 를 사용할 수 있는지 확인한다.")
    public void isBuilder() {
        Event event = Event.builder()
                .name("Spring REST API")
                .description("REST API Development")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    @DisplayName("java bean 스펙을 준수하는 지 확인한다.")
    public void javaBean() {
        // given
        String name = "Event";
        String desc = "Spring";

        // when
        Event event = new Event();
        event.setName(name);
        event.setDescription(desc);

        // then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(desc);
    }
}