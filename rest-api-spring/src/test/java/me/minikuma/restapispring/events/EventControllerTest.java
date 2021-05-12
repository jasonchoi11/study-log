package me.minikuma.restapispring.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    public void createEvent() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Java Study")
                .description("Java REST API Study")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5,11, 0, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitEnrollment(100)
                .location("강남역 D2 StartUp Factory")
                .build();

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    public void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Java Study")
                .description("Java REST API Study")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5,11, 0, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitEnrollment(100)
                .location("강남역 D2 StartUp Factory")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();
        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Java Study")
                .description("Java REST API Study")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5,9, 0, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .basePrice(1000000)
                .maxPrice(200)
                .limitEnrollment(100)
                .location("강남역 D2 StartUp Factory")
                .build();
        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

}
