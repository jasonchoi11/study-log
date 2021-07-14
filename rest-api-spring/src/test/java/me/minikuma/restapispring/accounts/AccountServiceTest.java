package me.minikuma.restapispring.accounts;

import me.minikuma.restapispring.common.TestDescription;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @TestDescription("사용자의 정보 조회를 정상적으로 성공하는 경우")
    public void findByUsername() {
        String username = "xxx@abc.com";
        String password = "test";
        // given
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        // 계정 정보 저장
        this.accountRepository.save(account);

        // when
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails findUser = userDetailsService.loadUserByUsername(username);

        // then
        Assertions.assertThat(findUser.getPassword()).isEqualTo(password);
    }

    @Test
    @TestDescription("사용자의 정보 조회를 실패하는 경우")
    public void findByUsernameFail() {
        String username = "xxx@xxx.com";
        // expected
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        // when
        accountService.loadUserByUsername(username);
    }
}