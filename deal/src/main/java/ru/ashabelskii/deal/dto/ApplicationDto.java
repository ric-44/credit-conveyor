package ru.ashabelskii.deal.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationDto(
        UUID id,
        ClientDto client,
        LocalDateTime createdAt) {
}
