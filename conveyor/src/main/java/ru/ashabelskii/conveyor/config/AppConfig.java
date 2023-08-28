package ru.ashabelskii.conveyor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @NotNull
    @DecimalMin("1")
    private BigDecimal baseRate;
}
