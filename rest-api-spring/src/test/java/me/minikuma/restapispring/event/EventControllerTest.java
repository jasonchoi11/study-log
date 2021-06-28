package me.minikuma.restapispring.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.minikuma.restapispring.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("정상적으로 이벤트를 생성한다.")
    public void createEvent() throws Exception {
        // Post Request Body
        EventDto event = EventDto.builder()
                .name("spring")
                .description("rest api spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 22, 10, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 6, 22, 10, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("상암 IG 센터")
                .build();

        mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
        ;
    }

    @Test
    @TestDescription("입력 받을 수 없는 값을 입력하는 경우에 에러(Bad_Request)가 발생한다.")
    public void createEvent_Bad_Request() throws Exception {
        // Post Request Body
        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("rest api spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 22, 10, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 6, 22, 10, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("상암 IG 센터")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력값이 아무것도 입력되지 않은 경우에 에러(Bad_Request)가 발생한다.")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto event = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(event))
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력된 값이 잘못된 경우에 에러가 발생한다.")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        // Post Request Body
        EventDto event = EventDto.builder()
                .name("spring")
                .description("rest api spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 24, 10, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 21, 10, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 6, 24, 10, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("상암 IG 센터")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(event))
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
        ;
    }
}
