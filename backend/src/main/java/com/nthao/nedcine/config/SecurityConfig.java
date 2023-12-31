package com.nthao.nedcine.config;

import com.nthao.nedcine.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@EnableManagementContext
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors() // Cho phép CORS từ tất cả các nguồn
                .and().authorizeRequests().requestMatchers(new AntPathRequestMatcher("/api/v1/auth/login"), new AntPathRequestMatcher("/api/v1/auth/register"), new AntPathRequestMatcher("/api/v1/users/**"),

                        new AntPathRequestMatcher("/api/v1/movies/**")
                        , new AntPathRequestMatcher("/api/v1/cinemas/**")
                        , new AntPathRequestMatcher("/api/v1/products/**"), new AntPathRequestMatcher("/api/v1/rooms/**"), new AntPathRequestMatcher("/api/v1/showtimes/**"), new AntPathRequestMatcher("/api/v1/seat-settings/**"), new AntPathRequestMatcher("/actuator/**"), new AntPathRequestMatcher("/api/v1/orders/**"), new AntPathRequestMatcher("/api/v1/emails/**")
                        , new AntPathRequestMatcher("/api/v1/comments/**")

                ).permitAll().anyRequest().authenticated().and().exceptionHandling() // Thêm phần xử lý ngoại lệ
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Sử dụng JwtAuthenticationEntryPoint khi không xác thực
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(authenticationProvider())

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
