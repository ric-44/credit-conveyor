package ru.ashabelskii.conveyor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.conveyor.api.ConveyorApi;
import ru.ashabelskii.conveyor.dto.*;
import ru.ashabelskii.conveyor.exception.ScoringException;
import ru.ashabelskii.conveyor.service.ConveyorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConveyorController implements ConveyorApi {

    private final ConveyorService conveyorService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> generateOffers(LoanApplicationRequestDto loanApplicationRequestDto,
                                                             HttpHeaders headers) {
        List<LoanOfferDto> loanOffers = conveyorService.generateOffers(loanApplicationRequestDto);
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<CreditDto> calculateCredit(ScoringDataDto scoringDataDto, HttpHeaders headers) {
        CreditDto creditDto = conveyorService.calculateCredit(scoringDataDto);
        return ResponseEntity.ok(creditDto);
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<ScoringErrorResponse> handleScoringException(ScoringException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ScoringErrorResponse(exception.getScoringRefuseCauses()));
    }
}
