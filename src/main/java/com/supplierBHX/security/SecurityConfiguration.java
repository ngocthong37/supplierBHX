package com.supplierBHX.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.supplierBHX.Enum.Role.ADMIN;
import static com.supplierBHX.Enum.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html",
                                "/api/v1/invoice/**",
                                "/api/v1/paymentResponse/**",
                                "/api/v1/paymentResponseDetail/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/quotation/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers("/api/v1/supply-capacity/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers("/api/v1/supplier/**").hasAnyRole(ADMIN.name(), MANAGER.name())
//                        .requestMatchers("/api/v1/invoice/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers("/api/v1/paymentResponse/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers("/api/v1/paymentResponseDetail/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/v1/ratingProduct/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/v1/ratingImage/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/v1/grnDetail/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/v1/quotation/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/v1/order/admin/**").hasAnyRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
