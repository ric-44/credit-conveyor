package ru.ashabelskii.application.dto;

import java.util.List;

public record PreScoringErrorResponse(
        List<String> causes
) {
}
