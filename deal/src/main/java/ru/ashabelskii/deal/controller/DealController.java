package ru.ashabelskii.deal.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.deal.api.DealApi;
import ru.ashabelskii.deal.dto.FinishRegistrationRequestDto;
import ru.ashabelskii.deal.dto.LoanApplicationRequestDto;
import ru.ashabelskii.deal.dto.LoanOfferDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {


    @Override
    public ResponseEntity<List<LoanOfferDto>> createApplication(LoanApplicationRequestDto loanApplicationRequestDto,
                                                                HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> applyOffer(LoanOfferDto loanOfferDto, HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> calculateCredit(UUID applicationId,
                                                FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                HttpHeaders headers) {
        throw new NotImplementedException();
    }
}
