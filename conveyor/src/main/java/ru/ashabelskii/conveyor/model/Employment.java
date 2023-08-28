package ru.ashabelskii.conveyor.model;

import lombok.Builder;

import java.math.BigDecimal;


/**
 * Работа
 *
 * @param salary                Зарплата
 * @param workExperienceTotal   Общий стаж работы
 * @param workExperienceCurrent Текущий стаж работы
 */
@Builder
public record Employment(
        EmploymentStatus status,
        BigDecimal salary,
        Position position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {

}
