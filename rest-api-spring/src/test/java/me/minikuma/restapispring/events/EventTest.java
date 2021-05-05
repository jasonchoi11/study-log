package me.minikuma.restapispring.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {
    @Test
    @DisplayName("Event 클래스에서 Builder 를 사용할 수 있는지 확인한다.")
    public void isBuilder() {
        Event event = Event.builder().build();
        assertThat(event).isNotNull();
    }
}