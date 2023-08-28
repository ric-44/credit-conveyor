package ru.ashabelskii.conveyor.service;

import org.springframework.stereotype.Service;
import ru.ashabelskii.conveyor.model.CreditInfo;
import ru.ashabelskii.conveyor.model.PaymentScheduleElement;
import ru.ashabelskii.conveyor.model.ScoringInfo;
import ru.ashabelskii.conveyor.service.util.CalcUtil;
import ru.ashabelskii.conveyor.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditService {

    public CreditInfo createCredit(ScoringInfo scoringInfo, BigDecimal rate) {
        Integer term = scoringInfo.term();
        BigDecimal amount = scoringInfo.amount();
        BigDecimal monthlyPayment = CalcUtil.calculateMonthlyPayment(rate, amount, term);
        List<PaymentScheduleElement> payments = getPaymentScheduleElements(rate, term, monthlyPayment, amount);
        BigDecimal totalAmount = CalcUtil.calculateTotalAmount(payments);
        BigDecimal psk = CalcUtil.calculatePSK(totalAmount, amount, term);

        return new CreditInfo(amount, term, monthlyPayment, rate, psk, totalAmount, scoringInfo.isInsuranceEnabled(),
                scoringInfo.isSalaryClient(), payments);
    }

    /** Получить график платежей */
    private List<PaymentScheduleElement> getPaymentScheduleElements(BigDecimal rate, Integer term,
                                                                    BigDecimal monthlyPayment, BigDecimal amount) {
        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>(term);

        LocalDate currentDate = TimeUtil.currentDate();
        BigDecimal remainingDebt = amount;

        for (int i = 1; i <= term; i++) {
            LocalDate startDate = currentDate.plusMonths(i);
            LocalDate endDate = startDate.plusMonths(1);
            long between = ChronoUnit.DAYS.between(startDate, endDate);
            int lengthOfYear = startDate.lengthOfYear();

            BigDecimal interestPayment = CalcUtil.calculateInterestPayment(rate, remainingDebt, between, lengthOfYear);
            BigDecimal debtPayment = getDebtPayment(monthlyPayment, interestPayment, remainingDebt);
            remainingDebt = remainingDebt.subtract(debtPayment);
            BigDecimal totalPayment = debtPayment.add(interestPayment);

            PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement(i, startDate, totalPayment,
                    debtPayment, interestPayment, remainingDebt);

            paymentScheduleElementList.add(paymentScheduleElement);
        }

        return paymentScheduleElementList;
    }

    /**
     * Получить основной долг
     * Основной долг = ежемесячный платеж - сумма процентов
     */
    private static BigDecimal getDebtPayment(BigDecimal monthlyPayment, BigDecimal interestPayment,
                                             BigDecimal remainingDebt) {
        // Если основной долг больше чем остаток выплат, то погашение основного долга = остатку выплат
        BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
        if (debtPayment.compareTo(remainingDebt) > 0) {
            return remainingDebt;
        }

        return debtPayment;
    }
}
