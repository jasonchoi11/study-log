package me.minikuma.restapispring.configs;

import me.minikuma.restapispring.accounts.Account;
import me.minikuma.restapispring.accounts.AccountRepository;
import me.minikuma.restapispring.accounts.AccountRole;
import me.minikuma.restapispring.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("인증 토큰 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        // given
        String username = "minikuma@xxx.com";
        String password = "minikuma";

        this.accountRepository.save(Account.builder()
                .email(username)
                .password(this.passwordEncoder.encode(password))
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build());

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId, clientSecret))
                    .param("username", username)
                    .param("password", password)
                    .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;
    }
}