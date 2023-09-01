package ru.ashabelskii.deal.integration.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.ashabelskii.deal.dto.EmploymentDto;
import ru.ashabelskii.deal.dto.GenderDto;
import ru.ashabelskii.deal.dto.MaritalDto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ScoringDataDto(
        @NotNull
        @DecimalMin("10000")
        @Digits(integer = 15, fraction = 2)
        @Schema(description = "Сумма кредита", example = "300000.00")
        BigDecimal amount,
        @NotNull
        @Min(value = 6)
        @Schema(description = "Срок кредита", example = "18")
        Integer term,
        @NotBlank
        @Size(min = 2, max = 30)
        @Schema(description = "Имя", example = "Сергей")
        String firstName,
        @NotBlank
        @Size(min = 2, max = 30)
        @Schema(description = "Фамилия", example = "Ершов")
        String lastName,
        @Size(min = 2, max = 30)
        @Schema(description = "Отчество", example = "Иванович")
        String middleName,
        @NotBlank
        @Size(min = 6, max = 6)
        @Schema(description = "Номер паспорта", example = "564333")
        String passportNumber,
        @NotBlank
        @Size(min = 4, max = 4)
        @Schema(description = "Серия паспорта", example = "4011")
        String passportSeries,
        @Schema(description = "Дата выдачи паспорта", example = "2006-06-21")
        LocalDate passportIssueDate,
        @Schema(description = "Место выдачи паспорта", example = "ТП №43 отдела УФМС России по Санкт-Петербургу и Ленинградской области")
        String passportIssueBranch,
        @NotNull
        @Schema(description = "День Рождения", example = "1992-06-21")
        LocalDate birthdate,
        @NotNull
        @Schema(description = "Количество иждивенцев", example = "1")
        Integer dependentAmount,
        @NotNull
        @Schema(description = "Зарплатный клиент?")
        Boolean isSalaryClient,
        @NotNull
        @Schema(description = "Застрахованный клиент?")
        Boolean isInsuranceEnabled,
        @NotNull
        @Schema(description = "Пол")
        GenderDto gender,
        @Valid
        @NotNull
        EmploymentDto employment,
        @NotNull
        @Schema(description = "Семейное положение")
        MaritalDto maritalStatus,
        @Schema(description = "Счет клиента", example = "A8902312381239")
        String account
) {

}