package ru.ashabelskii.conveyor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashabelskii.conveyor.config.AppConfig;
import ru.ashabelskii.conveyor.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(15);

    @InjectMocks
    private ScoringService scoringService;
    @Mock
    private AppConfig appConfig;

    @Test
    void whenRunScoringThenSuccess() {
        var scoringInfo = getDefaultScoringInfoBuilder().build();
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);

        var scoringResult = scoringService.start(scoringInfo);

        assertThat(scoringResult.isAvailability()).isTrue();
        assertThat(scoringResult.rate()).isEqualByComparingTo(BigDecimal.valueOf(12));
    }

    @Test
    void whenRunScoringWithRateEqualOneThenSuccess() {
        var scoringInfo = getDefaultScoringInfoBuilder().build();
        when(appConfig.getBaseRate()).thenReturn(BigDecimal.ONE);

        var scoringResult = scoringService.start(scoringInfo);

        assertThat(scoringResult.isAvailability()).isTrue();
        assertThat(scoringResult.rate()).isEqualByComparingTo(BigDecimal.ONE);
    }

    @Test
    void whenRunScoringWithWorkExperienceTotalLessThenAvailabilityFalse() {
        var workExperienceTotal = 5;
        var workExperienceCurrent = 5;
        var employment = getDefaultEmploymentBuilder()
                .workExperienceCurrent(workExperienceCurrent)
                .workExperienceTotal(workExperienceTotal)
                .build();
        var scoringInfo = getDefaultScoringInfoBuilder()
                .employment(employment)
                .build();
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);

        var scoringResult = scoringService.start(scoringInfo);

        assertThat(scoringResult.isAvailability()).isFalse();
    }

    @Test
    void whenRunScoringWithWorkExperienceCurrentLessThenAvailabilityFalse() {
        var workExperienceCurrent = 1;
        var employmentBuilder = getDefaultEmploymentBuilder()
                .workExperienceCurrent(workExperienceCurrent)
                .build();
        var scoringInfo = getDefaultScoringInfoBuilder()
                .employment(employmentBuilder)
                .build();
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);

        var scoringResult = scoringService.start(scoringInfo);

        assertThat(scoringResult.isAvailability()).isFalse();
    }

    @Test
    void whenRunScoringWithSalaryLessThenAvailabilityFalse() {
        var salary = BigDecimal.valueOf(10_000);
        var scoringInfo = getDefaultScoringInfoBuilder()
                .employment(getDefaultEmploymentBuilder().salary(salary).build())
                .build();
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);

        var scoringResult = scoringService.start(scoringInfo);
        assertThat(scoringResult.isAvailability()).isFalse();

    }

    private static ScoringInfo.ScoringInfoBuilder getDefaultScoringInfoBuilder() {
        return ScoringInfo.builder()
                .birthdate(LocalDate.now().minusYears(45))
                .amount(BigDecimal.valueOf(300_000))
                .term(6)
                .dependentAmount(1)
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .employment(getDefaultEmploymentBuilder().build())
                .maritalStatus(Marital.MARRIED);
    }

    private static Employment.EmploymentBuilder getDefaultEmploymentBuilder() {
        return Employment.builder()
                .salary(BigDecimal.valueOf(50_000))
                .workExperienceTotal(15)
                .workExperienceCurrent(10)
                .position(Position.WORKER)
                .status(EmploymentStatus.BUSINESS_OWNER);
    }
}