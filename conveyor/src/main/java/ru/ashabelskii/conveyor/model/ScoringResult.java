package ru.ashabelskii.conveyor.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record ScoringResult(
        BigDecimal rate,
        List<String> scoringRefuseCauses
) {
    public boolean isAvailability() {
        return Objects.isNull(scoringRefuseCauses) || scoringRefuseCauses.isEmpty();
    }
}
