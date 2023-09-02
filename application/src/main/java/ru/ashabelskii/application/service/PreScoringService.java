package ru.ashabelskii.application.service;

import org.springframework.stereotype.Service;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.exception.PreScoringException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PreScoringService {
    private static final String REGEX_NAME = "[A-Za-zА-Яа-яЁё\\-]+";
    private static final long MIN_AMOUNT = 10000;
    private static final int MIN_AGE = 18;
    private static final int MIN_TERM = 6;
    private static final String REGEX_PASSPORT_NUMBER = "\\d{6}";
    private static final String REGEX_PASSPORT_SERIES = "\\d{4}";

    public void validate(LoanApplicationRequestDto loanApplicationRequestDto) {
        List<String> causes = new ArrayList<>();
        if (!loanApplicationRequestDto.firstName().matches(REGEX_NAME)) {
            causes.add("firstName - incorrect format");
        }
        if (!loanApplicationRequestDto.lastName().matches(REGEX_NAME)) {
            causes.add("lastName - incorrect format");
        }
        if (Objects.nonNull(loanApplicationRequestDto.middleName())
                && !loanApplicationRequestDto.middleName().matches(REGEX_NAME)) {
            causes.add("MiddleName - incorrect format");
        }
        if (!loanApplicationRequestDto.passportNumber().matches(REGEX_PASSPORT_NUMBER)) {
            causes.add("passport number - incorrect format");
        }
        if (!loanApplicationRequestDto.passportSeries().matches(REGEX_PASSPORT_SERIES)) {
            causes.add("passport series - incorrect format");
        }
        if (ChronoUnit.YEARS.between(loanApplicationRequestDto.birthdate(), LocalDate.now()) < MIN_AGE) {
            causes.add("age < " + MIN_AGE);
        }
        if (loanApplicationRequestDto.amount().compareTo(BigDecimal.valueOf(MIN_AMOUNT)) < 0) {
            causes.add("credit amount < " + MIN_AMOUNT);
        }
        if (loanApplicationRequestDto.term() < MIN_TERM) {
            causes.add("term < " + MIN_TERM);
        }

        if (!causes.isEmpty()) {
            throw new PreScoringException(causes);
        }
    }
}
