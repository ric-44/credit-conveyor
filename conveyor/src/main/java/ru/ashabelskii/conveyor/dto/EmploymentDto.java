package ru.ashabelskii.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record EmploymentDto(
        @NotNull
        EmploymentStatusDto employmentStatus,
        @Schema(description = "ИНН", example = "784821858948")
        String employerINN,
        @NotNull
        @Schema(description = "Зарплата", example = "90000")
        BigDecimal salary,
        @NotNull
        PositionDto position,
        @NotNull
        @Schema(description = "Общий стаж работы", example = "15")
        Integer workExperienceTotal,
        @NotNull
        @Schema(description = "Текущий стаж работы", example = "15")
        Integer workExperienceCurrent
) {

}