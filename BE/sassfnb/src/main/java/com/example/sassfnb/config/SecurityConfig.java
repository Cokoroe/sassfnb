package com.example.sassfnb.config;

import com.example.sassfnb.security.CustomUserDetailsService;
import com.example.sassfnb.security.JwtAuthenticationFilter;
import com.example.sassfnb.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    private static final String[] PUBLIC = { "/api/auth/**", "/api/public/**" };
    private static final String[] ROOT = {
            "/api/root/**", "/api/permissions/**", "/api/users/**",
            "/api/categories/**", "/api/menu/**", "/api/tables/**", "/api/restaurants/**"
    };
    private static final String[] STAFF = {
            "/api/staff/**", "/api/table/**", "/api/kitchen/**", "/api/orders/**"
    };
    private static final String[] CUSTOMER = { "/api/customer/**", "/api/orders/**" };
    private static final String[] SUPPLIER = { "/api/supplier/**" };
    private static final String[] ADMIN_SYSTEM = { "/api/adminsystem/**" };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json");
                    res.getWriter().write("{\"message\":\"Token khong hop le hoac thieu token.\"}");
                }))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC).permitAll()
                        .requestMatchers(ROOT).hasAuthority("ROOT")
                        .requestMatchers(STAFF).hasAuthority("STAFF")
                        .requestMatchers(CUSTOMER).hasAuthority("CUSTOMER")
                        .requestMatchers(SUPPLIER).hasAuthority("SUPPLIER")
                        .requestMatchers(ADMIN_SYSTEM).hasAuthority("ADMINSYSTEM")
                        .anyRequest().authenticated())
                // QUAN TRỌNG: truyền PUBLIC vào filter để bỏ qua kiểm tra token cho route
                // public
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService, PUBLIC),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }
}
