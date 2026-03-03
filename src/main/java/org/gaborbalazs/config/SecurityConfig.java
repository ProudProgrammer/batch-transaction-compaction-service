package org.gaborbalazs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    private static final String USER = "user";
    private static final String ADMIN = "admin";
    private static final String PASSWORD = "password";
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.withUsername(USER)
                .password(encoder.encode(PASSWORD))
                .roles(ROLE_USER)
                .build();
        UserDetails admin = User.withUsername(ADMIN)
                .password(encoder.encode(PASSWORD))
                .roles(ROLE_USER, ROLE_ADMIN)
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
