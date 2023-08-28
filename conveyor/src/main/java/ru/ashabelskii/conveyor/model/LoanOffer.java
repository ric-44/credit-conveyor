package ru.ashabelskii.conveyor.model;

import java.math.BigDecimal;

/**
 * Кредитное предложение
 *
 * @param requestedAmount    Сумма кредита
 * @param term               Срок кредита
 * @param monthlyPayment     Ежемесячный платеж
 * @param rate               Ставка кредита
 * @param totalAmount        Сумма всех платежей
 * @param isSalaryClient     Зарплатный клиент?
 * @param isInsuranceEnabled Наличие страховки?
 */
public record LoanOffer(
        BigDecimal requestedAmount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        BigDecimal totalAmount,
        boolean isSalaryClient,
        boolean isInsuranceEnabled
) {

}
