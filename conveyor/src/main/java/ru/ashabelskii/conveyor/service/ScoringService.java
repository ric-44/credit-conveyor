package ru.ashabelskii.conveyor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.conveyor.config.AppConfig;
import ru.ashabelskii.conveyor.model.ScoringInfo;
import ru.ashabelskii.conveyor.model.ScoringResult;
import ru.ashabelskii.conveyor.util.TimeUtil;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static ru.ashabelskii.conveyor.model.EmploymentStatus.*;
import static ru.ashabelskii.conveyor.model.Marital.MARRIED;
import static ru.ashabelskii.conveyor.model.Marital.REMARRIED;

@Service
@RequiredArgsConstructor
public class ScoringService {

    private final AppConfig appConfig;

    public ScoringResult start(ScoringInfo scoringInfo) {
        List<String> scoringRefuseCauses = new ArrayList<>();
        BigDecimal currentRate = appConfig.getBaseRate();
        if (scoringInfo.amount().compareTo(
                scoringInfo.employment().salary().multiply(BigDecimal.valueOf(20))) > 0) {
            scoringRefuseCauses.add("Loan amount is more than 20 salaries → denial");
        }
        if (scoringInfo.employment().workExperienceTotal() < 12) {
            scoringRefuseCauses.add("Total experience of less than 12 months → denial");
        }
        if (scoringInfo.employment().workExperienceCurrent() < 3) {
            scoringRefuseCauses.add("Current experience less than 3 months → denial");
        }
        if (UNEMPLOYED == scoringInfo.employment().status()) {
            scoringRefuseCauses.add("Unemployed status → denial");
        }

        if (scoringInfo.isInsuranceEnabled()) {
            currentRate = currentRate.subtract(BigDecimal.ONE);
        }
        if (scoringInfo.isSalaryClient()) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(2));
        }

        if (SELF_EMPLOYED == scoringInfo.employment().status()) {
            currentRate = currentRate.add(BigDecimal.ONE);
        } else if (BUSINESS_OWNER == scoringInfo.employment().status()) {
            currentRate = currentRate.add(BigDecimal.valueOf(3));
        }

        if (MARRIED == scoringInfo.maritalStatus()) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(3));
        } else if (REMARRIED == scoringInfo.maritalStatus()) {
            currentRate = currentRate.add(BigDecimal.ONE);
        }
        if (scoringInfo.dependentAmount() > 1) {
            currentRate = currentRate.add(BigDecimal.ONE);
        }
        if (Period.between(scoringInfo.birthdate(), TimeUtil.currentDate()).getYears() > 60) {
            currentRate = currentRate.add(BigDecimal.ONE);
        }

        return new ScoringResult(checkRate(currentRate), scoringRefuseCauses);
    }

    private BigDecimal checkRate(BigDecimal currentRate) {
        if (currentRate.compareTo(BigDecimal.ONE) < 0) {
            return BigDecimal.ONE;
        }
        return currentRate;
    }
}
