package ru.ashabelskii.conveyor.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Данные для расчёта скоринга
 *
 * @param amount              Сумма кредита
 * @param term                Срок кредита
 * @param firstName           Имя
 * @param lastName            Фамилия
 * @param middleName          Отчество
 * @param passportNumber      Номер паспорта
 * @param passportSeries      Серия паспорта
 * @param passportIssueDate   Дата выдачи паспорта
 * @param passportIssueBranch Место выдачи паспорта
 * @param birthdate           День Рождения
 * @param dependentAmount     Количество иждивенцев
 * @param isSalaryClient      Зарплатный клиент?
 * @param isInsuranceEnabled  Застрахованный клиент?
 * @param gender              Пол
 * @param employment          Работа
 * @param maritalStatus       Семейное положение
 * @param account             Счет клиента
 */
@Builder
public record ScoringInfo(
        BigDecimal amount,
        Integer term,
        String firstName,
        String lastName,
        String middleName,
        String passportNumber,
        String passportSeries,
        LocalDate passportIssueDate,
        String passportIssueBranch,
        LocalDate birthdate,
        Integer dependentAmount,
        boolean isSalaryClient,
        boolean isInsuranceEnabled,
        Gender gender,
        Employment employment,
        Marital maritalStatus,
        String account
) {

}
