package ru.ashabelskii.deal.integration.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.ashabelskii.deal.dto.PaymentScheduleElementDto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

public record CreditDto(
        @DecimalMin("10000")
        @Digits(integer = 15, fraction = 2)
        @Schema(description = "Сумма кредита", example = "300000.00")
        BigDecimal amount,
        @Min(value = 6)
        @Schema(description = "Срок кредита", example = "18")
        Integer term,
        @Schema(description = "Ежемесячный платеж", example = "18715.44")
        BigDecimal monthlyPayment,
        @Schema(description = "Ставка кредита", example = "15")
        BigDecimal rate,
        @Schema(description = "Полная стоимость кредита", example = "8.7")
        BigDecimal psk,
        @Schema(description = "Наличие страховки?")
        Boolean isInsuranceEnabled,
        @Schema(description = "Зарплатный клиент?")
        Boolean isSalaryClient,
        @Schema(description = "График платежей")
        List<PaymentScheduleElementDto> paymentSchedule
) {

}