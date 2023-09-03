package ru.ashabelskii.deal.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmailMessage(
        UUID applicationId,
        String address,
        Theme theme,
        String text
) {

}
