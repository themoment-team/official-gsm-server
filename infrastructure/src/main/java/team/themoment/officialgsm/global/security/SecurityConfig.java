package team.themoment.officialgsm.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.themoment.officialgsm.global.security.filter.ExceptionHandlerFilter;
import team.themoment.officialgsm.global.security.filter.JwtRequestFilter;
import team.themoment.officialgsm.global.security.handler.CustomAuthenticationEntryPointHandler;
import team.themoment.officialgsm.global.security.service.OAuthService;
import team.themoment.officialgsm.admin.controller.auth.manager.CookieManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuthService oAuthService;
    private final CookieManager cookieManager;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .anyRequest().permitAll()
        );

        http
                .oauth2Login(oauth -> oauth.userInfoEndpoint(u -> u.userService(oAuthService)));
        http
                .oauth2Login(oauth -> oauth.failureHandler(authenticationFailureHandler));

        http
                .exceptionHandling(exception ->
                exception.authenticationEntryPoint(new CustomAuthenticationEntryPointHandler(cookieManager)));

        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtRequestFilter.class);

        return http.build();
    }
}
