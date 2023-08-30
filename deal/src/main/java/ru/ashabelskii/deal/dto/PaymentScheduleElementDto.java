package ru.ashabelskii.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentScheduleElementDto(
        @Schema(description = "Номер платежа", example = "1")
        Integer number,
        @Schema(description = "Дата предстоящего платежа")
        LocalDate date,
        @Schema(description = "Общая сумма оплаты", example = "11000.00")
        BigDecimal totalPayment,
        @Schema(description = "Процент платежа", example = "11000.00")
        BigDecimal interestPayment,
        @Schema(description = "Погашение основного долга", example = "11000.00")
        BigDecimal debtPayment,
        @Schema(description = "Остаток основного долга", example = "200000.00")
        BigDecimal remainingDebt
) {

}