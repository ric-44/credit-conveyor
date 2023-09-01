package ru.ashabelskii.deal.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class LoanOffer {
    private UUID applicationId;
    private BigDecimal requestedAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal totalAmount;
    private Boolean isSalaryClient;
    private Boolean isInsuranceEnabled;
}