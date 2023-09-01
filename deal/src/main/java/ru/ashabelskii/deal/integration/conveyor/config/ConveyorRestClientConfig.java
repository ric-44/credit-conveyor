package ru.ashabelskii.deal.integration.conveyor.config;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ConveyorRestClientConfig {
    @NotBlank
    private String url;
    @Min(0)
    @NotNull
    private Integer connectTimeout;
    @Min(1)
    @NotNull
    private Integer readTimeout;
    @Min(1)
    @NotNull
    private Integer writeTimeout;
}
