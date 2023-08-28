package ru.ashabelskii.conveyor.dto;

import java.util.List;

public record ScoringErrorResponse(
        List<String> scoringRefuseCauses
) {
}
