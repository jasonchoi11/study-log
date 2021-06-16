package me.minikuma.restapispring.configs;

import lombok.RequiredArgsConstructor;
import me.minikuma.restapispring.accounts.AccountService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.accountService)
                .passwordEncoder(this.passwordEncoder)
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/docs/index.html");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 모든 정적 파일
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .anonymous() // 익명사용자 허용
//                .and()
//            .formLogin() // form 인증 사용
//                .and()
//            .authorizeRequests() // 허용할 요청 세팅
//                .mvcMatchers(HttpMethod.GET, "/api/**").authenticated() // /api 하위 모든 경로를 인증함
//                .anyRequest().authenticated() // 나머지 인증 처리
//        ;
//    }
}
