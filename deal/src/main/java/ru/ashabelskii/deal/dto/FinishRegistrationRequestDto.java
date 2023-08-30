package ru.ashabelskii.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
public record FinishRegistrationRequestDto(
        @NotNull
        @Schema(description = "Количество иждивенцев", example = "1")
        Integer dependentAmount,
        @NotNull
        @Schema(description = "Пол")
        GenderDto gender,
        @NotNull
        @Schema(description = "Дата выдачи паспорта", example = "2006-06-21")
        LocalDate passportIssueDate,
        @NotNull
        @Schema(description = "Место выдачи паспорта",
                example = "ТП №43 отдела УФМС России по Санкт-Петербургу и Ленинградской области")
        String passportIssueBranch,
        @NotNull
        @Valid
        EmploymentDto employment,
        @NotNull
        @Schema(description = "Семейное положение")
        MaritalDto maritalStatus,
        @Schema(description = "Счет клиента", example = "A8902312381239")
        String account
) {

}