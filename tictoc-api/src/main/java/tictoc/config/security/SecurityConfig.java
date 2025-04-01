package tictoc.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tictoc.config.CorsFilter;
import tictoc.config.security.exception.ExceptionHandlerFilter;
import tictoc.config.security.exception.JwtAuthenticationEntryPoint;
import tictoc.config.security.filter.JwtAuthenticationFilter;
import tictoc.config.security.jwt.util.JwtProvider;


@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CorsFilter corsFilter;
//        private final CorsConfig corsConfig;
    private static final String[] whiteList = {
            "/api/v1/member/login",
            "/swagger-ui/**"
    };

    // Security 필터 체인 구성 메서드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (REST API이므로 불필요)
                .formLogin(AbstractHttpConfigurer::disable) // Form 기반 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화

                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안함 (JWT 기반)

                .exceptionHandling(
                        exceptionHandlingConfigurer ->
                                exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // 인증 실패 시 처리 핸들러 지정

                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(whiteList).permitAll() // whiteList는 인증 없이 접근 가능
                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .anyRequest().authenticated())

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) // CORS 필터는 제일 앞에 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class) // 예외 처리 필터는 JWT 필터보다 먼저 실행
                .build();
    }

    // 정적 리소스 및 whitelist는 Spring Security가 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(whiteList);
    }
}
