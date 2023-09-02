package ru.ashabelskii.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.application.api.ApplicationApi;
import ru.ashabelskii.application.dto.LoanApplicationRequestDto;
import ru.ashabelskii.application.dto.LoanOfferDto;
import ru.ashabelskii.application.dto.PreScoringErrorResponse;
import ru.ashabelskii.application.exception.PreScoringException;
import ru.ashabelskii.application.service.ApplicationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApi {

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> createApplication(LoanApplicationRequestDto loanApplicationRequestDto,
                                                                HttpHeaders headers) {
        List<LoanOfferDto> application = applicationService.createApplication(loanApplicationRequestDto);
        return ResponseEntity.ok(application);
    }

    @Override
    public ResponseEntity<Void> applyOffer(LoanOfferDto loanOfferDto, HttpHeaders headers) {
        applicationService.applyOffer(loanOfferDto);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(PreScoringException.class)
    public ResponseEntity<PreScoringErrorResponse> handleScoringException(PreScoringException exception) {
        log.info("PreScoring error, causes: {}", exception.getCauses());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new PreScoringErrorResponse(exception.getCauses()));
    }
}
