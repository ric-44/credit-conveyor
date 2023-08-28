package ru.ashabelskii.conveyor.service.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ashabelskii.conveyor.model.PaymentScheduleElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalcUtil {
    private static final long MONTHS_IN_YEAR = 12;

    /**
     * Расчет ежемесячной оплаты (аннуитетный платеж)
     * Аннуитетный платеж = Сумма кредита * коэффициент аннуитета
     */
    public static BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term) {
        BigDecimal factorAnnuity = calculateFactorAnnuity(rate, term);
        BigDecimal monthlyPayment = amount.multiply(factorAnnuity);
        return normalize(monthlyPayment);
    }

    /**
     * Расчет суммы процентов
     * Остаток долга × Процентная ставка × Количество дней в месяце / Количество дней в году
     */
    public static BigDecimal calculateInterestPayment(BigDecimal rate, BigDecimal remainingDebt, long between,
                                                      int lengthOfYear) {
        BigDecimal decimalRate = convertToDecimal(rate);
        return remainingDebt
                .multiply(decimalRate)
                .multiply(BigDecimal.valueOf(between))
                .divide(BigDecimal.valueOf(lengthOfYear), 2, RoundingMode.HALF_UP);
    }

    /**
     * Расчёт ∑ всех платежей
     */
    public static BigDecimal calculateTotalAmount(List<PaymentScheduleElement> payments) {
        return payments.stream()
                .map(PaymentScheduleElement::totalPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }
    public static BigDecimal calculatePSK(BigDecimal totalAmount, BigDecimal amount, Integer term) {
        BigDecimal termInYears = BigDecimal.valueOf(term).divide(BigDecimal.valueOf(MONTHS_IN_YEAR), 2,
                RoundingMode.CEILING);
        BigDecimal intermediateCoefficient = totalAmount.divide(amount, 2, RoundingMode.CEILING)
                .subtract(BigDecimal.ONE);
        return intermediateCoefficient.divide(termInYears, 3, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Расчет коэффициента аннуитета
     * (МП * (1 + МП) ^ КП) / ((1 + МП) ^ КП - 1)
     * МП - Месячная процентная ставка
     * КП - Количество платежей
     */
    private static BigDecimal calculateFactorAnnuity(BigDecimal rate, Integer term) {
        BigDecimal decimalMonthlyPaymentRate = convertToDecimal(calculateMonthlyPaymentRate(rate));
        BigDecimal part = BigDecimal.ONE.add(decimalMonthlyPaymentRate).pow(term);              // (1 + МП) ^ КП
        BigDecimal divisible = decimalMonthlyPaymentRate.multiply(part);                        // (МП * (1 + МП) ^ КП)
        BigDecimal divider = part.subtract(BigDecimal.ONE);                                     // ((1 + МП) ^ КП - 1)
        return divisible.divide(divider, 10, RoundingMode.HALF_UP);
    }

    /**
     * Расчет месячной процентной ставки:
     * Месячная процентная ставка = Процентная ставка (в годовых) / 12 месяцев
     * 15% / 12 = 1,25%
     */
    private static BigDecimal calculateMonthlyPaymentRate(BigDecimal rate) {
        return rate.divide(BigDecimal.valueOf(MONTHS_IN_YEAR), 10, RoundingMode.HALF_UP);
    }

    /**
     * Перевод процентов в десятичную дробь
     * 1,25 / 100 = 0,0125
     */
    private static BigDecimal convertToDecimal(BigDecimal interest) {
        return interest.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
    }

    /**
     * Округление денежных средств
     * 10,2567 -> 10,26
     */
    private static BigDecimal normalize(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_UP);
    }
}
