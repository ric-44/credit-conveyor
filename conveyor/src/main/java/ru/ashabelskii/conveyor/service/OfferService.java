package ru.ashabelskii.conveyor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.conveyor.config.AppConfig;
import ru.ashabelskii.conveyor.model.LoanOffer;
import ru.ashabelskii.conveyor.model.LoanRequest;
import ru.ashabelskii.conveyor.service.util.CalcUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final AppConfig appConfig;

    public List<LoanOffer> createOffers(LoanRequest loanRequest) {
        BigDecimal baseRate = appConfig.getBaseRate();

        LoanOffer offer1 = calculate(loanRequest, baseRate, true, true);
        LoanOffer offer2 = calculate(loanRequest, baseRate, false, true);
        LoanOffer offer3 = calculate(loanRequest, baseRate, true, false);
        LoanOffer offer4 = calculate(loanRequest, baseRate, false, false);
        return List.of(offer1, offer2, offer3, offer4);
    }

    private LoanOffer calculate(LoanRequest loanRequest, BigDecimal baseRate, boolean insurance, boolean salaryClient) {
        BigDecimal rate = calculateRate(baseRate, insurance, salaryClient);
        BigDecimal monthlyPayment = CalcUtil.calculateMonthlyPayment(rate, loanRequest.amount(), loanRequest.term());
        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(loanRequest.term()));

        return new LoanOffer(
                loanRequest.amount(),
                loanRequest.term(),
                monthlyPayment,
                rate,
                totalAmount,
                salaryClient,
                insurance
        );
    }

    private BigDecimal calculateRate(BigDecimal rate, boolean insurance, boolean salaryClient) {
        BigDecimal currentRate = rate;
        if (insurance) {
            currentRate = currentRate.subtract(BigDecimal.ONE);
        }
        if (salaryClient) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(2));
        }
        return currentRate;
    }
}
