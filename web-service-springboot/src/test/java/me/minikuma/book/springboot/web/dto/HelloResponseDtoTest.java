package me.minikuma.book.springboot.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloResponseDtoTest {
    @Test
    @DisplayName("롬복 기능 테스트 (스터디용)")
    public void lombok_getter_test() {
        // given
        String name = "test";
        int account = 10;

        // when
        HelloResponseDto helloResponseDto = new HelloResponseDto(name, account);

        // then
        assertThat(helloResponseDto.getName()).isEqualTo(name);
        assertThat(helloResponseDto.getAccount()).isEqualTo(account);
    }
}