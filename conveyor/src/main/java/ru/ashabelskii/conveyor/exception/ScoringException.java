package ru.ashabelskii.conveyor.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ScoringException extends RuntimeException {

    private final List<String> scoringRefuseCauses;

    public ScoringException(List<String> scoringRefuseCauses) {
        this.scoringRefuseCauses = scoringRefuseCauses;
    }
}