package ru.ashabelskii.conveyor.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.conveyor.dto.LoanApplicationRequestDto;
import ru.ashabelskii.conveyor.dto.LoanOfferDto;
import ru.ashabelskii.conveyor.model.LoanOffer;
import ru.ashabelskii.conveyor.model.LoanRequest;

import java.util.List;

@Component
public class OfferMapper {
    public LoanRequest mapLoanRequest(LoanApplicationRequestDto loanApplicationRequest) {
        return new LoanRequest(loanApplicationRequest.amount(), loanApplicationRequest.term());
    }

    public List<LoanOfferDto> mapOfferResponse(List<LoanOffer> loanOffers) {
        return loanOffers.stream()
                .map(this::mapLoanOfferDto)
                .toList();
    }

    private LoanOfferDto mapLoanOfferDto(LoanOffer loanOffer) {
        return new LoanOfferDto(
                loanOffer.requestedAmount(),
                loanOffer.term(),
                loanOffer.monthlyPayment(),
                loanOffer.rate(),
                loanOffer.totalAmount(),
                loanOffer.isSalaryClient(),
                loanOffer.isInsuranceEnabled()
        );
    }
}
