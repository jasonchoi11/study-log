package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource messageSource;

    @Test
    @DisplayName("메시지 조회한다(한국어 출력)")
    void helloMessage() {
        String result = messageSource.getMessage("hello", null, Locale.ROOT);
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("영어 메시지 조회한다")
    void helloEnMessage() {
        String result = messageSource.getMessage("hello", null, Locale.ENGLISH);
        Assertions.assertThat(result).isEqualTo("hello");
    }

    @Test
    @DisplayName("Not Found: 미 존재하는 코드를 입력한다")
    void notFoundMessageCode() {
        Assertions.assertThatThrownBy(() -> messageSource.getMessage("no_code", null, Locale.ROOT))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    @DisplayName("기본 메시지를 출력한다")
    void notFoundMessageCodeDefaultMessage() {
        String result = messageSource.getMessage("no_code", null, "기본 메시지", Locale.ROOT);
        Assertions.assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    @DisplayName("매개변수를 넘겨주는 경우 (치환)")
    void argumentMessage() {
        String result = messageSource.getMessage("hello.name", new Object[]{"Spring"}, Locale.ROOT);
        Assertions.assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    @DisplayName("기본 언어")
    void defaultLanguage() {
        Assertions.assertThat(messageSource.getMessage("hello", null, Locale.ROOT)).isEqualTo("안녕");
        Assertions.assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    @DisplayName("영어 언어 선택")
    void englishLanguage() {
        Assertions.assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
