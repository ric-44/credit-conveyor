package ru.ashabelskii.conveyor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashabelskii.conveyor.model.*;
import ru.ashabelskii.conveyor.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    private final static LocalDate FIXED_DATE = LocalDate.of(2023, 1, 1);
    private static final BigDecimal RATE = BigDecimal.valueOf(15);

    private final CreditService creditService = new CreditService();

    @Test
    void whenCreateCreditThenSuccess() {
        TimeUtil.useMockTime(FIXED_DATE.atStartOfDay(), ZoneId.systemDefault());
        var scoringInfo = getDefaultScoringInfoBuilder().build();

        var credit = creditService.createCredit(scoringInfo, RATE);

        assertThat(credit.amount()).isEqualByComparingTo(BigDecimal.valueOf(300_000));
        assertThat(credit.term()).isEqualTo(6);
        assertThat(credit.rate()).isEqualByComparingTo(BigDecimal.valueOf(15));
        assertThat(credit.monthlyPayment()).isEqualByComparingTo(BigDecimal.valueOf(52210.14));
        assertThat(credit.psk()).isEqualByComparingTo(BigDecimal.TEN);

        assertPayments(credit, scoringInfo);
    }

    private static void assertPayments(CreditInfo credit, ScoringInfo scoringInfo) {
        var payments = credit.paymentSchedule();
        assertThat(payments).hasSize(scoringInfo.term());
        assertPayment(payments, 1, 52210.14, 48758.09, 3452.05, 251241.91);
        assertPayment(payments, 2, 52210.14, 49009.39, 3200.75, 202232.52);
        assertPayment(payments, 3, 52210.14, 49716.86, 2493.28, 152515.66);
        assertPayment(payments, 4, 52210.14, 50267.13, 1943.01, 102248.53);
        assertPayment(payments, 5, 52210.14, 50949.54, 1260.60, 51298.99);
        assertPayment(payments, 6, 51952.53, 51298.99, 653.54, 0.00);
    }

    private static void assertPayment(List<PaymentScheduleElement> payments, int number, double totalPayment,
                                      double debtPayment, double interestPayment, double remainingDebt) {
        assertThat(payments)
                .filteredOn(paymentScheduleElement -> paymentScheduleElement.number() == number)
                .singleElement()
                .satisfies(payment -> {
                            assertThat(payment.totalPayment()).isEqualByComparingTo(BigDecimal.valueOf(totalPayment));
                            assertThat(payment.debtPayment()).isEqualByComparingTo(BigDecimal.valueOf(debtPayment));
                            assertThat(payment.interestPayment()).isEqualByComparingTo(BigDecimal.valueOf(interestPayment));
                            assertThat(payment.remainingDebt()).isEqualByComparingTo(BigDecimal.valueOf(remainingDebt));
                        }
                );
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