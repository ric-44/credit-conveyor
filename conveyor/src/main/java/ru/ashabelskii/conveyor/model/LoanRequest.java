package ru.ashabelskii.conveyor.model;

import java.math.BigDecimal;


/**
 * Кредит. Входные данные
 *
 * @param amount Сумма кредита
 * @param term   Срок кредита
 */
public record LoanRequest(
        BigDecimal amount,
        Integer term
) {

}
