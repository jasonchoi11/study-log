package me.minikuma.book.springboot.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("hello 문자열이 반환된다.")
    public void get_hello_string() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(hello))
        ;
    }

    @Test
    @DisplayName("hello dto 값을 요청받아 입력받은 값을 반환한다.")
    public void get_hello_dto() throws Exception {
        String name = "hello";
        int account = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("account", String.valueOf(account))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.account").exists())
                .andExpect(jsonPath("$.account", is(account)))
        ;
    }
}