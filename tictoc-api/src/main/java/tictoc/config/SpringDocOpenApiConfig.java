package tictoc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

// http://localhost:8080/swagger-ui/index.html
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "TicToc API",
                description = "TicToc API 명세서",
                version = "1.0.0")
)
@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP) // 인증 타입: HTTP 인증
                .scheme("bearer") // 인증 방식: Bearer 토큰
                .bearerFormat("JWT") // 토큰 포맷: JWT (UI 표시 목적)
                .in(SecurityScheme.In.HEADER) // 헤더에 포함시킬 것임
                .name("Authorization"); // 헤더 이름: Authorization
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }

    @Bean
    public GroupedOpenApi userGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch(
                        "/api/v1/member/login",
                        "/api/v1/member/schedule"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi auctionGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("Auction")
                .displayName("Auction")
                .pathsToMatch("/api/v1/member/auction/**")
                .build();
    }

    @Bean
    public GroupedOpenApi bidGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("Bid")
                .displayName("Bid")
                .pathsToMatch("/api/v1/member/bid/**")
                .build();
    }
}