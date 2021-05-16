package me.minikuma.restapispring.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    // JUnit 5 기준 Parameter Test
    @ParameterizedTest(name = "{index} => basePrice={0}, maxPrice={1}, isFree={2}")
    @MethodSource("isFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        // given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // when
        event.update();

        // then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private static Object[] isFree() {
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0, false},
                new Object[] {0, 100, false}
        };
    }

    // JUnit 5 기준 Parameter Test
    @ParameterizedTest(name = "{index} => location={0}, offline={1}")
    @MethodSource("isOffline")
    public void testOffline(String location, boolean isOffline) {
        // given
        Event event = Event.builder()
                .location(location)
                .build();

        // when
        event.update();

        // then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    private static Stream<Arguments> isOffline() {
        return Stream.of(
                Arguments.of("강남역", true),
                Arguments.of(null, false),
                Arguments.of("", false)
        );
    }
}