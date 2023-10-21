package ru.ashabelskii.deal.dto;

import java.time.LocalDate;

public record ClientDto(
        String firstName,
        String lastName,
        String middleName,
        LocalDate birthdate
) {
}