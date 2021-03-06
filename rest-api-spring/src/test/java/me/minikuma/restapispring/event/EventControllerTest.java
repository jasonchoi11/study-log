package me.minikuma.restapispring.event;

import me.minikuma.restapispring.accounts.Account;
import me.minikuma.restapispring.accounts.AccountRepository;
import me.minikuma.restapispring.accounts.AccountRole;
import me.minikuma.restapispring.accounts.AccountService;
import me.minikuma.restapispring.common.BaseControllerTest;
import me.minikuma.restapispring.common.TestDescription;
import me.minikuma.restapispring.configs.AppProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AppProperties appProperties;

    @Before
    public void cleanUp() {
        this.eventRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    @TestDescription("??????????????? ???????????? ????????????.")
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
                .location("?????? IG ??????")
                .build();

        mockMvc.perform(post("/api/events")
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
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
                                linkWithRel("update-events").description("link to update an existing event"),
                                linkWithRel("profile").description("link to profile")
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
                        relaxedResponseFields(
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
                                fieldWithPath("_links.update-events.href").description("Link to update event list"),
                                fieldWithPath("_links.profile.href").description("Link to profile")
                        )
                 ))
        ;
    }



    @Test
    @TestDescription("?????? ?????? ??? ?????? ?????? ???????????? ????????? ??????(Bad_Request)??? ????????????.")
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
                .location("?????? IG ??????")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("???????????? ???????????? ???????????? ?????? ????????? ??????(Bad_Request)??? ????????????.")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto event = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(event))
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("????????? ?????? ????????? ????????? ????????? ????????????.")
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
                .location("?????? IG ??????")
                .build();

        this.mockMvc.perform(post("/api/events")
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(event))
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("content[0].objectName").exists())
                .andExpect(jsonPath("content[0].defaultMessage").exists())
                .andExpect(jsonPath("content[0].code").exists())
                .andExpect(jsonPath("_links.index").exists())
        ;
    }

    @Test
    @TestDescription("30??? ???????????? 10?????? ???????????? ??? ????????? ????????? ????????? ????????????")
    public void queryEvents() throws Exception {
        // given
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
    @TestDescription("30??? ???????????? 10?????? ???????????? ??? ????????? ????????? ????????? ????????????")
    public void queryEventsWithAuthentication() throws Exception {
        // given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when
        this.mockMvc.perform(get("/api/events")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true)) // ????????? ?????????
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
                .andExpect(jsonPath("_links.create-events").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @TestDescription("????????? ????????? ????????? ????????????")
    public void getEvent() throws Exception {
        // given
        Account account = this.createAccount();
        Event event = this.generateEvent(100, account);

        // when & then
        this.mockMvc.perform(get("/api/events/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-event"))
        ;
    }

    @Test
    @TestDescription("?????? ???????????? ???????????? ??? 404 ????????????")
    public void getEvent404() throws Exception {
        this.mockMvc.perform(get("/api/events/123455666"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @TestDescription("???????????? ??????????????? ????????????")
    public void updateEvent() throws Exception {
        // given
        Account account = this.createAccount();
        Event event = this.generateEvent(200, account);

        String eventName = "updated event";
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setName(eventName);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(false))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-event"))
        ;
    }

    @Test
    @TestDescription("???????????? ???????????? ?????? ????????? ?????? ??????")
    public void updateEvent400_Empty() throws Exception {
        // given
        Event event = this.generateEvent(400);
        EventDto eventDto = EventDto.builder().build();

        // when
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("???????????? ?????? ?????? ?????? ?????? ????????? ?????? ??????")
    public void updateEvent400_Wrong() throws Exception {
        // given
        Event event = this.generateEvent(400);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        // when & then
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                    .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("???????????? ?????? ???????????? ????????? ??? ??????")
    public void updateEvent404() throws Exception {
        // given
        Event event = this.generateEvent(400);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);

        // when & then
        this.mockMvc.perform(put("/api/events/133455566")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    // private method

    private Event buildEvent(int index) {
        return Event.builder()
                    .name("event " + index)
                    .description("test event")
                    .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 22, 10, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                    .beginEventDateTime(LocalDateTime.of(2021, 6, 22, 10, 0, 0))
                    .endEventDateTime(LocalDateTime.of(2021, 6, 23, 10, 0, 0))
                    .basePrice(100)
                    .maxPrice(200)
                    .limitOfEnrollment(100)
                    .location("?????? IG ??????")
                    .free(false)
                    .offline(true)
                    .eventStatus(EventStatus.DRAFT)
                    .build();
    }

    private Event generateEvent(int index, Account account) {
        Event event = buildEvent(index);
        event.setManager(account);
        return this.eventRepository.save(event);
    }

    private Event generateEvent(int index) {
        Event event = buildEvent(index);
        return this.eventRepository.save(event);
    }

    private String getBearerToken(boolean needToCreateAccount) throws Exception {
        return "Bearer " + getAccessToken(needToCreateAccount);
    }

    private String getAccessToken(boolean needToCreateAccount) throws Exception {
        // given
        if (needToCreateAccount) {
            createAccount();
        }
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", appProperties.getUserUsername())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password"));

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser jsonParser = new Jackson2JsonParser();
        return jsonParser.parseMap(responseBody).get("access_token").toString();
    }

    private Account createAccount() {
        Account account = Account.builder()
                .email(appProperties.getUserUsername())
                .password(appProperties.getUserPassword())
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        return this.accountService.saveAccount(account);
    }
}
