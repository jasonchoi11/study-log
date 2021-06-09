package me.minikuma.restapispring.configs;

import me.minikuma.restapispring.accounts.Account;
import me.minikuma.restapispring.accounts.AccountRole;
import me.minikuma.restapispring.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                accountService.saveAccount(Account.builder()
                        .email("minikuma@xxx.com")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .password("minikuma")
                        .build());
            }
        };
    }
}
