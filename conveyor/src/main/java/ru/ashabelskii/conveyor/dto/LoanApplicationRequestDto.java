package ru.ashabelskii.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record LoanApplicationRequestDto(
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
        @NotNull
        @Schema(description = "День Рождения", example = "1992-06-21")
        LocalDate birthdate,
        @Email
        @Schema(description = "Электронный адрес", example = "email@ya.ru")
        String email
) {

}