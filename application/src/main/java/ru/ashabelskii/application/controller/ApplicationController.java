package ru.ashabelskii.application.controller;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.application.api.ApplicationApi;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.dto.LoanOfferDto;

import java.util.List;

@RestController
public class ApplicationController implements ApplicationApi {

    @Override
    public ResponseEntity<List<LoanOfferDto>> createApplication(LoanApplicationRequestDto loanApplicationRequestDto,
                                                                HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> applyOffer(LoanOfferDto loanOfferDto, HttpHeaders headers) {
        throw new NotImplementedException();
    }
}
