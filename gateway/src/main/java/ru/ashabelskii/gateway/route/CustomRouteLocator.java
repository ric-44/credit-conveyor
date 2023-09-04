package ru.ashabelskii.gateway.route;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ashabelskii.gateway.config.IntegrationConfig;

@Configuration
@RequiredArgsConstructor
public class CustomRouteLocator {

    private final IntegrationConfig config;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("deal/**",
                        route -> route.path("/deal/**")
                                .uri(config.getDealUrl() + "/deal/**"))
                .route("application/**",
                        route -> route.path("/application/**")
                                .uri(config.getApplicationUrl() + "/application/**"))
                .build();
    }
}
