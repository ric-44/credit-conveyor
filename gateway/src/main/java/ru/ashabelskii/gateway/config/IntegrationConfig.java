package ru.ashabelskii.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "integration")
public class IntegrationConfig {
    @NotBlank
    private String applicationUrl;
    @NotBlank
    private String dealUrl;
}
