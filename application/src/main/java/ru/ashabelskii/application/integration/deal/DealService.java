package ru.ashabelskii.application.integration.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.dto.LoanOfferDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealClient dealClient;

    public List<LoanOfferDto> createApplication(LoanApplicationRequestDto loanApplicationRequestDto) {
        return dealClient.createApplication(loanApplicationRequestDto);
    }

    public void applyOffer(LoanOfferDto loanOfferDto) {
        dealClient.applyOffer(loanOfferDto);
    }
}
