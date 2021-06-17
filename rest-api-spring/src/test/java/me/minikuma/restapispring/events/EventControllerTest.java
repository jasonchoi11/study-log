package me.minikuma.restapispring.events;

import me.minikuma.restapispring.accounts.Account;
import me.minikuma.restapispring.accounts.AccountRepository;
import me.minikuma.restapispring.accounts.AccountRole;
import me.minikuma.restapispring.accounts.AccountService;
import me.minikuma.restapispring.common.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @BeforeEach
    public void cleanUp() {
        this.eventRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적으로 이벤트를 생성하는 테스트")
    public void createEvent() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Java Study")
                .description("Java REST API Study")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5,11, 0, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 5, 11, 0, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitEnrollment(100)
                .location("강남역 D2 StartUp Factory")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-event").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andDo(document("create-event",
                    links(
                            linkWithRel("self").description("link to self"),
                            linkWithRel("query-event").description("link to query event"),
                            linkWithRel("update-event").description("link to update an existing event"),
                            linkWithRel("profile").description("link to profile")
                    ),
                    requestHeaders(
                            headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    requestFields(
                            fieldWithPath("name").description("Name of new event"),
                            fieldWithPath("description").description("description of new event"),
                            fieldWithPath("beginEnrollmentDateTime").description("date time of beginEnrollmentDateTime"),
                            fieldWithPath("closeEnrollmentDateTime").description("date time of closeEnrollmentDateTime"),
                            fieldWithPath("beginEventDateTime").description("date time of beginEventDateTime"),
                            fieldWithPath("endEventDateTime").description("date time of endEventDateTime"),
                            fieldWithPath("location").description("location of new event"),
                            fieldWithPath("basePrice").description("base price of new event"),
                            fieldWithPath("maxPrice").description("max price of new event"),
                            fieldWithPath("limitEnrollment").description("limit of event")
                    ),
                    responseHeaders(
                            headerWithName(HttpHeaders.LOCATION).description("location header"),
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    responseFields(
                            fieldWithPath("id").description("identifier id"),
                            fieldWithPath("name").description("Name of new event"),
                            fieldWithPath("description").description("description of new event"),
                            fieldWithPath("beginEnrollmentDateTime").description("date time of beginEnrollmentDateTime"),
                            fieldWithPath("closeEnrollmentDateTime").description("date time of closeEnrollmentDateTime"),
                            fieldWithPath("beginEventDateTime").description("date time of beginEventDateTime"),
                            fieldWithPath("endEventDateTime").description("date time of endEventDateTime"),
                            fieldWithPath("location").description("location of new event"),
                            fieldWithPath("basePrice").description("base price of new event"),
                            fieldWithPath("maxPrice").description("max price of new event"),
                            fieldWithPath("limitEnrollment").description("limit of event"),
                            fieldWithPath("offline").description("this event is offline of not"),
                            fieldWithPath("free").description("this event is free of not"),
                            fieldWithPath("eventStatus").description("status of event"),
                            fieldWithPath("_links.self.href").description("link to self"),
                            fieldWithPath("_links.query-event.href").description("link to query event"),
                            fieldWithPath("_links.update-event.href").description("link to update event"),
                            fieldWithPath("_links.profile.href").description("link to profile")
                    )
                ))
        ;
    }

    private String getAccessToken() throws Exception {
        // given
        String username = "minikuma@xxx.com";
        String password = "minikuma";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(account);

        String clientId = "myApp";
        String clientSecret = "pass";

        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
        ;
        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }

    @Test
    @DisplayName("입력 받을 수 없는 값을 사용하는 경우에 에러가 발생하는 테스트")
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
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();
        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
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
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("contents[0].objectName").exists())
                .andExpect(jsonPath("contents[0].defaultMessage").exists())
                .andExpect(jsonPath("contents[0].code").exists())
                .andExpect(jsonPath("_links.index").exists())
        ;
    }

    @Test
    @DisplayName("30개의 이벤트를 10개씩 두번째 페이지 조회하기")
    public void queryEvents() throws Exception {
        // given : 이벤트 30개 준비
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when
        this.mockMvc.perform(get("/api/events")
                    .param("page", "1")
                    .param("size", "10")
                    .param("sort", "name,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))

        ;
    }

    @Test
    @DisplayName("기존의 이벤트 하나 조회하기")
    public void getEvent() throws Exception {
        // given
        Event event = this.generateEvent(100);

        // when & then
        this.mockMvc.perform(get("/api/events/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
        ;
    }

    @Test
    @DisplayName("없는 이벤트를 조회하면 404 를 응답한다")
    public void getEvent404() throws Exception {
        // when & then
        this.mockMvc.perform(get("/api/events/45693939"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("이벤트를 정상적으로 수정하기")
    public void updateEvent() throws Exception {
        // given
        Event newEvent = this.generateEvent(200);
        String eventName= "Updated Event";
        EventDto eventDto = this.modelMapper.map(newEvent, EventDto.class);
        eventDto.setName(eventName);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", newEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-event"))
        ;
    }

    @Test
    @DisplayName("입력값이 잘못된 경우에 이벤트 수정 실패 (bad request)")
    public void updateEvent400() throws Exception {
        // given
        Event newEvent = this.generateEvent(300);
        EventDto eventDto = new EventDto();

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", newEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력값이 잘못된 경우에 이벤트 수정 실패 (bad request)")
    public void updateEvent400_Wrong() throws Exception {
        // given
        Event newEvent = this.generateEvent(400);
        EventDto eventDto = new EventDto();
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", newEvent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 수정 실패")
    public void updateEvent404() throws Exception {
        // given
        Event newEvent = this.generateEvent(500);
        EventDto eventDto = this.modelMapper.map(newEvent, EventDto.class);

        // when & then
        this.mockMvc.perform(put("/api/events/123455")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }


    private Event generateEvent(int index) {
        Event event = Event.builder()
                .name("event " + index)
                .description("test event " + "#" + index)
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 5,11, 0, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 5, 10, 0, 0, 0))
                .endEventDateTime(LocalDateTime.of(2021, 5, 11, 0, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitEnrollment(100)
                .location("강남역 D2 StartUp Factory")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();
        return this.eventRepository.save(event);
    }
}
