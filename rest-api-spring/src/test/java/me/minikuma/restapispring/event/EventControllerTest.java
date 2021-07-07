package me.minikuma.restapispring.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.minikuma.restapispring.common.RestDocsConfiguration;
import me.minikuma.restapispring.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
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
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-events").exists())
                .andDo(document("create-event",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query events"),
                                linkWithRel("update-events").description("link to update an existing event")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("Description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("Date time of begin event"),
                                fieldWithPath("closeEnrollmentDateTime").description("Date time of close event"),
                                fieldWithPath("beginEventDateTime").description("Date time of begin new event"),
                                fieldWithPath("endEventDateTime").description("Date time of end new event"),
                                fieldWithPath("location").description("Location of event"),
                                fieldWithPath("basePrice").description("Base Price of event"),
                                fieldWithPath("maxPrice").description("Max Price of event"),
                                fieldWithPath("limitOfEnrollment").description("Limit of event")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("response header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type header")
                        ),
                        responseFields(
                                fieldWithPath("id").description("ID of new event"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("Description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("Date time of begin event"),
                                fieldWithPath("closeEnrollmentDateTime").description("Date time of close event"),
                                fieldWithPath("beginEventDateTime").description("Date time of begin new event"),
                                fieldWithPath("endEventDateTime").description("Date time of end new event"),
                                fieldWithPath("location").description("Location of event"),
                                fieldWithPath("basePrice").description("Base Price of event"),
                                fieldWithPath("maxPrice").description("Max Price of event"),
                                fieldWithPath("limitOfEnrollment").description("Limit of event"),
                                fieldWithPath("free").description("It tells if this event is free or not"),
                                fieldWithPath("offline").description("It tells if this event is offline or not"),
                                fieldWithPath("eventStatus").description("Event Status"),
                                fieldWithPath("_links.self.href").description("Link to self"),
                                fieldWithPath("_links.query-events.href").description("Link to query event list"),
                                fieldWithPath("_links.update-events.href").description("Link to update event list")
                        )
                 ))
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
