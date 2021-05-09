package me.minikuma.restapispring.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void 이벤트를만든다() throws Exception {
        Event event = Event.builder()
                .name("Java Study")
                .description("Java REST API Study")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 05, 10, 00, 00, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 05,11, 00, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2021, 05, 10, 00, 00, 00))
                .endEventDateTime(LocalDateTime.of(2021, 05, 10, 00, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitEnrollment(100)
                .location("강남역 D2 StartUp Factory")
                .build();

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }
}
