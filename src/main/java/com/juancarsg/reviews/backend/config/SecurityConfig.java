package com.juancarsg.reviews.backend.config;

import com.juancarsg.reviews.backend.filter.JWTFilter;
import com.juancarsg.reviews.backend.service.UserService;
import com.juancarsg.reviews.backend.util.Cons;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.juancarsg.reviews.backend.controller.AuthController.API_PATH_AUTH;
import static com.juancarsg.reviews.backend.controller.CategoryController.API_PATH_CATEGORIES;
import static com.juancarsg.reviews.backend.controller.CommerceController.API_PATH_COMMERCES;
import static com.juancarsg.reviews.backend.controller.ReviewController.API_PATH_REVIEWS;
import static com.juancarsg.reviews.backend.controller.ScheduleController.API_PATH_SCHEDULES;
import static com.juancarsg.reviews.backend.controller.UserController.API_PATH_USERS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JWTFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(API_PATH_AUTH, "/error").permitAll()

                        .requestMatchers(HttpMethod.GET, API_PATH_CATEGORIES).permitAll()
                        .requestMatchers(HttpMethod.POST, API_PATH_CATEGORIES).hasAnyAuthority(Cons.USER_MODERATOR, Cons.USER_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_PATH_CATEGORIES).hasAnyAuthority(Cons.USER_ADMIN)

                        .requestMatchers(HttpMethod.GET, API_PATH_COMMERCES).permitAll()
                        .requestMatchers(HttpMethod.POST, API_PATH_COMMERCES).hasAnyAuthority(Cons.USER_MODERATOR, Cons.USER_ADMIN)
                        .requestMatchers(HttpMethod.PATCH, API_PATH_COMMERCES).hasAnyAuthority(Cons.USER_MODERATOR, Cons.USER_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_PATH_COMMERCES).hasAnyAuthority(Cons.USER_ADMIN)

                        .requestMatchers(HttpMethod.GET, API_PATH_REVIEWS).authenticated()
                        .requestMatchers(HttpMethod.POST, API_PATH_REVIEWS).authenticated()
                        .requestMatchers(HttpMethod.PATCH, API_PATH_REVIEWS).authenticated()
                        .requestMatchers(HttpMethod.DELETE, API_PATH_REVIEWS).authenticated()

                        .requestMatchers(HttpMethod.GET, API_PATH_SCHEDULES).permitAll()
                        .requestMatchers(HttpMethod.POST, API_PATH_SCHEDULES).hasAnyAuthority(Cons.USER_MODERATOR, Cons.USER_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_PATH_SCHEDULES).hasAnyAuthority(Cons.USER_ADMIN)

                        .requestMatchers(HttpMethod.GET, API_PATH_USERS).authenticated()
                        .requestMatchers(HttpMethod.POST, API_PATH_USERS).permitAll()
                        .requestMatchers(HttpMethod.PATCH, API_PATH_USERS).authenticated()
                        .requestMatchers(HttpMethod.PUT, API_PATH_USERS).hasAnyAuthority(Cons.USER_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, API_PATH_USERS).hasAnyAuthority(Cons.USER_ADMIN)

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (UserService userService,
                                                        PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

}
