package ru.ashabelskii.application.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class PreScoringException extends RuntimeException {

    private static final String PRE_SCORING_ERROR = "Ошибка прескоринга";
    private final List<String> causes;

    public PreScoringException(List<String> causes) {
        super(PRE_SCORING_ERROR);
        this.causes = causes;
    }
}
