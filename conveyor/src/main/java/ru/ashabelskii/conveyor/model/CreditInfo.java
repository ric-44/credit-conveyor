package ru.ashabelskii.conveyor.model;


import java.math.BigDecimal;
import java.util.List;

/**
 * Кредит
 *
 * @param amount             Сумма кредита
 * @param term               Срок кредита
 * @param monthlyPayment     Ежемесячный платеж
 * @param rate               Ставка кредита
 * @param psk                Полная стоимость кредита
 * @param totalAmount        Сумма всех платежей
 * @param isInsuranceEnabled Наличие страховки?
 * @param isSalaryClient     Зарплатный клиент?
 * @param paymentSchedule    График платежей
 */
public record CreditInfo(
        BigDecimal amount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        BigDecimal psk,
        BigDecimal totalAmount,
        boolean isInsuranceEnabled,
        boolean isSalaryClient,
        List<PaymentScheduleElement> paymentSchedule
) {

}
