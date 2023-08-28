package ru.ashabelskii.conveyor.model;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Платёж
 *
 * @param number          Номер платежа
 * @param date            Дата предстоящего платежа
 * @param totalPayment    Общая сумма оплаты
 * @param debtPayment     Погашение основного долга
 * @param interestPayment Процент платежа
 * @param remainingDebt   Остаток основного долга
 */
public record PaymentScheduleElement(
        Integer number,
        LocalDate date,
        BigDecimal totalPayment,
        BigDecimal debtPayment,
        BigDecimal interestPayment,
        BigDecimal remainingDebt
) {

}