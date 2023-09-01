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
import ru.ashabelskii.deal.service.DealService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {

    private final DealService dealService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> createApplication(LoanApplicationRequestDto loanApplicationRequestDto,
                                                                HttpHeaders headers) {
        List<LoanOfferDto> application = dealService.createApplication(loanApplicationRequestDto);
        return ResponseEntity.ok(application);
    }

    @Override
    public ResponseEntity<Void> applyOffer(LoanOfferDto loanOfferDto, HttpHeaders headers) {
        dealService.applyOffer(loanOfferDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> calculateCredit(UUID applicationId,
                                                FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                HttpHeaders headers) {
        dealService.calculateCredit(applicationId, finishRegistrationRequestDto);
        return ResponseEntity.ok().build();
    }
}
