package ru.ashabelskii.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanOfferDto(
        @Schema(description = "Идентификатор заявления", example = "ec1fe120-3f2d-4676-b39e-40556cad0473")
        UUID applicationId,
        @Schema(description = "Запрашиваемая сумма кредита", example = "300000.00")
        BigDecimal requestedAmount,
        @Schema(description = "Срок кредита", example = "18")
        Integer term,
        @Schema(description = "Ежемесячный платеж", example = "18999.03")
        BigDecimal monthlyPayment,
        @Schema(description = "Ставка кредита", example = "18")
        BigDecimal rate,
        @Schema(description = "Сумма всех платежей", example = "341982.54")
        BigDecimal totalAmount,
        @Schema(description = "Зарплатный клиент?")
        Boolean isSalaryClient,
        @Schema(description = "Наличие страховки?")
        Boolean isInsuranceEnabled
) {

}