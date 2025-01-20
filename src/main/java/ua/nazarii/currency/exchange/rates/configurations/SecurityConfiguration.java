package ua.nazarii.currency.exchange.rates.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.nazarii.currency.exchange.rates.entities.UserEntity;
import ua.nazarii.currency.exchange.rates.enums.Authorities;
import ua.nazarii.currency.exchange.rates.repositories.UserRepository;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return (String username) -> {
            Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

            if (optionalUserEntity.isEmpty()) {
                throw new UsernameNotFoundException("");
            }

            return optionalUserEntity.get();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.PUT, "/api/rates", "/api/rates/")
                                .hasAuthority(Authorities.PROVIDER.getAuthority())
                                .anyRequest()
                                .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
