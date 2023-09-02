package ru.ashabelskii.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.dto.LoanOfferDto;
import ru.ashabelskii.application.integration.deal.DealService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final PreScoringService preScoringService;
    private final DealService dealService;

    public List<LoanOfferDto> createApplication(LoanApplicationRequestDto loanApplicationRequestDto) {
        preScoringService.validate(loanApplicationRequestDto);
        return dealService.createApplication(loanApplicationRequestDto);
    }

    public void applyOffer(LoanOfferDto loanOfferDto) {
        dealService.applyOffer(loanOfferDto);
    }
}
